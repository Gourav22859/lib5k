package io.github.frc5024.purepursuit;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import io.github.frc5024.purepursuit.pathgen.Path;

/**
 * A lookahead finder for finding new points in a 2D path.
 * 
 * <br>
 * <br>
 * This is a Java implementation of
 * https://github.com/AtsushiSakai/PythonRobotics/blob/9274aacefb8fb52feac544fc26d075046f703afd/PathTracking/pure_pursuit/pure_pursuit.py#L77-L10
 */
public class Follower {

    // Path to follow
    private Path m_path;

    // Lookahead settings
    private double m_lookaheadDist, m_lookaheadGain;
    private Integer m_lastLookaheadIndex = null;

    // Drivebase info
    private double m_drivebaseWidth;
    private Pose2d m_lastPose = null;

    /**
     * Create a path follower
     * 
     * @param path              Path to follow
     * @param lookaheadDistance Lookahead distance
     * @param lookaheadGain     Lookahead gain
     * @param drivebaseWidth    Drivebase width
     */
    public Follower(Path path, double lookaheadDistance, double lookaheadGain, double drivebaseWidth) {
        this.m_path = path;
        this.m_lookaheadDist = lookaheadDistance;
        this.m_lookaheadGain = lookaheadGain;
        this.m_drivebaseWidth = drivebaseWidth;

    }

    public void setLookaheadDistance(double distance) {
        this.m_lookaheadDist = distance;
    }

    public double getLookaheadDistance() {
        return m_lookaheadDist;
    }

    public void setLookaheadGain(double gain) {
        this.m_lookaheadGain = gain;
    }

    public double getLookaheadGain() {
        return m_lookaheadGain;
    }

    public double getDrivebaseWidth() {
        return m_drivebaseWidth;
    }

    /**
     * Reset the follower
     */
    public void reset() {
        m_lastLookaheadIndex = null;
        m_lastPose = null;
    }

    public Translation2d getNextPoint(Pose2d robotPose) {

        // Cast the pose up to a drivebase state
        Translation2d robotVector = robotPose.getTranslation();

        // Max nearest pose
        Translation2d nearest = new Translation2d(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);

        // Check if this is the first run
        if (m_lastLookaheadIndex == null) {

            // Search for the nearest pose
            for (int i = 0; i < m_path.getPoses().length; i++) {

                // Determine diff
                double dx = robotVector.getX() - m_path.getPoses()[i].getX();
                double dy = robotVector.getY() - m_path.getPoses()[i].getY();

                // Determine distance
                double d = Math.hypot(dx, dy);

                // If d is closer than the nearest, we have found a new nearest point
                if (d < Math.hypot(robotVector.getX() - nearest.getX(), robotVector.getY() - nearest.getY())) {

                    // Set the nearest translation
                    nearest = m_path.getPoses()[i];

                    // Set the lookahead index
                    m_lastLookaheadIndex = i;
                }
            }
        } else {

            // Determine the distance from the current point
            double thisDist = robotVector.getDistance(m_path.getPoses()[m_lastLookaheadIndex]);

            // Tracker for path pose index
            int nextIndex = m_lastLookaheadIndex;

            // Look for a new pose
            // TODO: make this a FOR loop starting at m_lastLookaheadIndex
            while (true) {

                // Attempt to incr our search index
                nextIndex = ((nextIndex + 1) < m_path.getPoses().length) ? nextIndex + 1 : nextIndex;

                // Find the distance to the new pose
                double nextDist = robotVector.getDistance(m_path.getPoses()[nextIndex]);

                // If this new pose is closer, choose it
                if (thisDist < nextDist || nextIndex + 1 == m_path.getPoses().length) {
                    nearest = m_path.getPoses()[nextIndex];
                    break;
                }

                // Update the current distance
                thisDist = nextDist;
            }

            // Set the last lookahead
            m_lastLookaheadIndex = nextIndex;

        }

        // Determine our velocity from the last pose
        double v;
        if (m_lastPose != null) {
            double dx = robotPose.getTranslation().getX() - m_lastPose.getTranslation().getX();
            double dy = robotPose.getTranslation().getY() - m_lastPose.getTranslation().getY();

            // Calc V
            v = Math.hypot(dx, dy);
        } else {
            v = 0.0;
        }

        // Set the last pose
        m_lastPose = robotPose;

        // Set a Lookahead
        double L = 0.0;
        double LF = m_lookaheadGain * v * m_lookaheadDist;

        // Look for target
        int ind = m_lastLookaheadIndex;
        while ((LF > L) && (ind + 1) < m_path.getPoses().length) {
            // Determine L
            L = robotVector.getDistance(m_path.getPoses()[ind]);

            // Update ind
            ind++;
        }

        // Limit index
        if (m_lastLookaheadIndex >= ind) {
            ind = m_lastLookaheadIndex;
        }
        if (ind >= m_path.getPoses().length) {
            ind = m_path.getPoses().length - 1;
        }

        // Return the found pose
        return m_path.getPoses()[ind];

    }

    public Translation2d getFinalPose() {
        return m_path.getPoses()[m_path.getPoses().length - 1];
    }
}