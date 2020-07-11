package io.github.frc5024.lib5k.hardware.generic.sensors;

import edu.wpi.first.wpilibj.DigitalSource;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import io.github.frc5024.lib5k.hardware.common.sensors.simulation.EncoderSimUtil;
import io.github.frc5024.lib5k.hardware.common.sensors.interfaces.EncoderSimulation;

public class GenericEncoder extends Encoder implements EncoderSimulation {

    private int cpr;
    private boolean phase;
    private EncoderSimUtil sim;

    /**
     * Encoder constructor. Construct a Encoder given a and b channels.
     *
     * <p>
     * The encoder will start counting immediately.
     *
     * @param channelA         The a channel DIO channel. 0-9 are on-board, 10-25
     *                         are on the MXP port
     * @param channelB         The b channel DIO channel. 0-9 are on-board, 10-25
     *                         are on the MXP port
     * @param reverseDirection represents the orientation of the encoder and inverts
     *                         the output values if necessary so forward represents
     *                         positive values.
     */
    public GenericEncoder(final int channelA, final int channelB, boolean reverseDirection, int cpr) {
        super(channelA, channelB, reverseDirection);
        setCPR(cpr);
    }

    /**
     * Encoder constructor. Construct a Encoder given a and b channels.
     *
     * <p>
     * The encoder will start counting immediately.
     *
     * @param channelA The a channel digital input channel.
     * @param channelB The b channel digital input channel.
     */
    public GenericEncoder(final int channelA, final int channelB, int cpr) {
        super(channelA, channelB);
        setCPR(cpr);
    }

    /**
     * Encoder constructor. Construct a Encoder given a and b channels.
     *
     * <p>
     * The encoder will start counting immediately.
     *
     * @param channelA         The a channel digital input channel.
     * @param channelB         The b channel digital input channel.
     * @param reverseDirection represents the orientation of the encoder and inverts
     *                         the output values if necessary so forward represents
     *                         positive values.
     * @param encodingType     either k1X, k2X, or k4X to indicate 1X, 2X or 4X
     *                         decoding. If 4X is selected, then an encoder FPGA
     *                         object is used and the returned counts will be 4x the
     *                         encoder spec'd value since all rising and falling
     *                         edges are counted. If 1X or 2X are selected then a
     *                         m_counter object will be used and the returned value
     *                         will either exactly match the spec'd count or be
     *                         double (2x) the spec'd count.
     */
    public GenericEncoder(final int channelA, final int channelB, boolean reverseDirection,
            final EncodingType encodingType, int cpr) {
        super(channelA, channelB, reverseDirection, encodingType);
        setCPR(cpr);
    }

    /**
     * Encoder constructor. Construct a Encoder given a and b channels. Using an
     * index pulse forces 4x encoding
     *
     * <p>
     * The encoder will start counting immediately.
     *
     * @param channelA         The a channel digital input channel.
     * @param channelB         The b channel digital input channel.
     * @param indexChannel     The index channel digital input channel.
     * @param reverseDirection represents the orientation of the encoder and inverts
     *                         the output values if necessary so forward represents
     *                         positive values.
     */
    public GenericEncoder(final int channelA, final int channelB, final int indexChannel, boolean reverseDirection,
            int cpr) {
        super(channelA, channelB, indexChannel, reverseDirection);
        setCPR(cpr);
    }

    /**
     * Encoder constructor. Construct a Encoder given a and b channels. Using an
     * index pulse forces 4x encoding
     *
     * <p>
     * The encoder will start counting immediately.
     *
     * @param channelA     The a channel digital input channel.
     * @param channelB     The b channel digital input channel.
     * @param indexChannel The index channel digital input channel.
     */
    public GenericEncoder(final int channelA, final int channelB, final int indexChannel, int cpr) {
        super(channelA, channelB, indexChannel);
        setCPR(cpr);
    }

    /**
     * Encoder constructor. Construct a Encoder given a and b channels as digital
     * inputs. This is used in the case where the digital inputs are shared. The
     * Encoder class will not allocate the digital inputs and assume that they
     * already are counted.
     *
     * <p>
     * The encoder will start counting immediately.
     *
     * @param sourceA          The source that should be used for the a channel.
     * @param sourceB          the source that should be used for the b channel.
     * @param reverseDirection represents the orientation of the encoder and inverts
     *                         the output values if necessary so forward represents
     *                         positive values.
     */
    public GenericEncoder(DigitalSource sourceA, DigitalSource sourceB, boolean reverseDirection, int cpr) {
        super(sourceA, sourceB, reverseDirection);
        setCPR(cpr);
    }

    /**
     * Encoder constructor. Construct a Encoder given a and b channels as digital
     * inputs. This is used in the case where the digital inputs are shared. The
     * Encoder class will not allocate the digital inputs and assume that they
     * already are counted.
     *
     * <p>
     * The encoder will start counting immediately.
     *
     * @param sourceA The source that should be used for the a channel.
     * @param sourceB the source that should be used for the b channel.
     */
    public GenericEncoder(DigitalSource sourceA, DigitalSource sourceB, int cpr) {
        super(sourceA, sourceB);
        setCPR(cpr);
    }

    /**
     * Encoder constructor. Construct a Encoder given a and b channels as digital
     * inputs. This is used in the case where the digital inputs are shared. The
     * Encoder class will not allocate the digital inputs and assume that they
     * already are counted.
     *
     * <p>
     * The encoder will start counting immediately.
     *
     * @param sourceA          The source that should be used for the a channel.
     * @param sourceB          the source that should be used for the b channel.
     * @param reverseDirection represents the orientation of the encoder and inverts
     *                         the output values if necessary so forward represents
     *                         positive values.
     * @param encodingType     either k1X, k2X, or k4X to indicate 1X, 2X or 4X
     *                         decoding. If 4X is selected, then an encoder FPGA
     *                         object is used and the returned counts will be 4x the
     *                         encoder spec'd value since all rising and falling
     *                         edges are counted. If 1X or 2X are selected then a
     *                         m_counter object will be used and the returned value
     *                         will either exactly match the spec'd count or be
     *                         double (2x) the spec'd count.
     */
    public GenericEncoder(DigitalSource sourceA, DigitalSource sourceB, boolean reverseDirection,
            final EncodingType encodingType, int cpr) {
        super(sourceA, sourceB, reverseDirection, encodingType);
        setCPR(cpr);
    }

    /**
     * Encoder constructor. Construct a Encoder given a, b and index channels as
     * digital inputs. This is used in the case where the digital inputs are shared.
     * The Encoder class will not allocate the digital inputs and assume that they
     * already are counted.
     *
     * <p>
     * The encoder will start counting immediately.
     *
     * @param sourceA          The source that should be used for the a channel.
     * @param sourceB          the source that should be used for the b channel.
     * @param indexSource      the source that should be used for the index channel.
     * @param reverseDirection represents the orientation of the encoder and inverts
     *                         the output values if necessary so forward represents
     *                         positive values.
     */
    public GenericEncoder(DigitalSource sourceA, DigitalSource sourceB, DigitalSource indexSource,
            boolean reverseDirection, int cpr) {
        super(sourceA, sourceB, indexSource, reverseDirection);
        setCPR(cpr);
    }

    /**
     * Encoder constructor. Construct a Encoder given a, b and index channels as
     * digital inputs. This is used in the case where the digital inputs are shared.
     * The Encoder class will not allocate the digital inputs and assume that they
     * already are counted.
     *
     * <p>
     * The encoder will start counting immediately.
     *
     * @param sourceA     The source that should be used for the a channel.
     * @param sourceB     the source that should be used for the b channel.
     * @param indexSource the source that should be used for the index channel.
     */
    public GenericEncoder(DigitalSource sourceA, DigitalSource sourceB, DigitalSource indexSource, int cpr) {
        super(sourceA, sourceB, indexSource);
        setCPR(cpr);
    }

    private void setCPR(int cpr) {

        // Set CPR
        this.cpr = cpr;

        // Set distance per pulse to 1/cpr. This makes *cpr* pulses equal to 1 unit (or
        // 1 rotation)
        super.setDistancePerPulse(1 / cpr);
    }

    @Override
    public void initSimulationDevice(SpeedController controller, double gearbox_ratio, double max_rpm,
            double ramp_time) {
        sim = new EncoderSimUtil("Generic Encoder", super.getFPGAIndex(), cpr, controller, gearbox_ratio, max_rpm,
                ramp_time);
    }

    @Override
    public void update() {
        sim.update();

    }

    @Override
    public void setPhaseInverted(boolean inverted) {
        // Handle simulation vs reality
        if (sim != null && sim.simReady()) {
            sim.setInverted(inverted);
        } else {
            setReverseDirection(inverted);
            this.phase = inverted;
        }

    }

    @Override
    public boolean getInverted() {
        // Handle simulation
        if (sim != null && sim.simReady()) {
            return sim.getInverted();
        }
        return this.phase;
    }

    @Override
    public double getPosition() {
        // Handle simulation
        if (sim != null && sim.simReady()) {
            return sim.getRotations();
        }
        return super.get() / cpr;
    }

    @Override
    public double getVelocity() {
        // Handle simulation
        if (sim != null && sim.simReady()) {
            return sim.getVelocity();
        }
        return super.getRate();
    }

}