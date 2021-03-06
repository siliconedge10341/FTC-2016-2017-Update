package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Dival Banerjee on 9/7/2016.
 */

// NOTES //
    // SENSORS //
        // Distance Sensor
        // Three Dimensional Video Sensor
        // Color Sensor
        // Push Button Sensor
    // MOTORS //
        // Front Right
        // Front Left
        // Back Right
        // Back Left
        // Launch Motor
        // Scoop Motor
        // Left Hand Motor
        // Right Hand Motor
    //

public class DemoOp extends OpMode{


    //to avoid the overuse of magic numbers

    //drive motors
    DcMotor motorLeft1;
    DcMotor motorRight1;

    //function motors
    DcMotor motorTape;
    DcMotor motorBrush;

    // DcMotor motorSlide;
    DcMotor motorConveyor;

    //servomotors
    Servo tapearm;

    double servopos1;
    public DemoOp() {
    }

    //initialization routine
    @Override
    public void init() {



        motorLeft1 = hardwareMap.dcMotor.get("lmotor_1");
        motorRight1 = hardwareMap.dcMotor.get("rmotor_1");

        //lmotor_1 ----> LeftMotor
        //rmotor_1 ----> RightMotor

        motorLeft1.setDirection(DcMotor.Direction.FORWARD);
        motorRight1.setDirection(DcMotor.Direction.FORWARD);

        //Turn the collector


        //TODO design a power-on self test
        //Power ON Self Test

    }

    //main function body
    @Override
    public void loop() {
        /*
            DRIVE MOTOR CODE
         */
        // throttle: left_stick_y ranges from -1 to 1, where -1 is full up, and
        // 1 is full down
        // direction: left_stick_x ranges from -1 to 1, where -1 is full left

        //FIXME kind of a kludge, recheck hardware to see if motors were mixed up
        //FIXME b/c x and y axes are flippped
        float throttle = -gamepad1.left_stick_x;
        float direction = gamepad1.left_stick_y;
        //Silicon Edge: We Specialize in Kludges
        float right = throttle - direction;
        float left = throttle + direction;
        // clip the right/left values so that the values never exceed +/- 1
        right = Range.clip(right, -1, 1);
        left = Range.clip(left, -1, 1);
        // scale the joystick value to make it easier to control
        // the robot more precisely at slower speedy
        right = (float)scaleInput(right);
        left =  (float)scaleInput(left);
        //Sets theo
        // write the values to the motors
        motorLeft1.setPower(left);
        motorRight1.setPower(right);

    }


    @Override
    public void stop() {

    }

    /*
     * This method scales the joystick input so for low joystick values, the
     * scaled value is less than linear.  This is to make it easier to drive
     * the robot more precisely at slower speeds.
     */
    double scaleInput(double dVal)  {
        double[] scaleArray = { 0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00 };

        // get the corresponding index for the scaleInput array.
        int index = (int) (dVal * 16.0);

        // index should be positive.
        if (index < 0) {
            index = -index;
        }

        // index cannot exceed size of array minus 1.
        if (index > 16) {
            index = 16;
        }

        // get value from the array.
        double dScale = 0.0;
        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }

        // return scaled value.
        return dScale;
    }

}