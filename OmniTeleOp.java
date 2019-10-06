package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.robot.Robot;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.util.Range;


//    Robot wheel mapping:
//        X FRONT X
//        X           X
//        X  FL       FR  X
//        X
//        XXX
//        X
//        X  BL       BR  X
//        X           X
//        X       X
//        */
@TeleOp
//@Disabled
public class OmniTeleOp extends LinearOpMode {
    private org.firstinspires.ftc.teamcode.HardwareMapping robot = new org.firstinspires.ftc.teamcode.HardwareMapping();


    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

            // left stick controls direction
            // right stick X controls rotation

            float gamepad1LeftY = -gamepad1.left_stick_y;
            float gamepad1LeftX = gamepad1.left_stick_x;
            float gamepad1RightX = gamepad1.right_stick_x;

            // holonomic formulas

            float FrontLeftPrep = -gamepad1LeftY - gamepad1LeftX - gamepad1RightX;
            float FrontRightPrep = gamepad1LeftY - gamepad1LeftX - gamepad1RightX;
            float BackRightPrep = gamepad1LeftY + gamepad1LeftX - gamepad1RightX;
            float BackLeftPrep = -gamepad1LeftY + gamepad1LeftX - gamepad1RightX;

            // clip the right/left values so that the values never exceed +/- 1
            FrontRightPrep = Range.clip(FrontRightPrep, -1, 1);
            FrontLeftPrep = Range.clip(FrontLeftPrep, -1, 1);
            BackLeftPrep = Range.clip(BackLeftPrep, -1, 1);
            BackRightPrep = Range.clip(BackRightPrep, -1, 1);

            double FrontLeft = Math.pow(FrontLeftPrep, 3);
            double FrontRight = Math.pow(FrontRightPrep, 3);
            double BackLeft = Math.pow(BackLeftPrep, 3);
            double BackRight = Math.pow(BackRightPrep, 3);


            // write the values to the motors
            robot.frontRightMotor.setPower(FrontRight);
            robot.frontLeftMotor.setPower(FrontLeft);
            robot.backLeftMotor.setPower(BackLeft);
            robot.backRightMotor.setPower(BackRight);


            /*
             * Telemetry for debugging
             */
            telemetry.addData("Text", "*** Robot Data***");
            telemetry.addData("Joy XL YL XR", String.format("%.2f", gamepad1LeftX) + " " +
                    String.format("%.2f", gamepad1LeftY) + " " + String.format("%.2f", gamepad1RightX));
            telemetry.addData("f left pwr", "front left  pwr: " + String.format("%.2f", FrontLeft));
            telemetry.addData("f right pwr", "front right pwr: " + String.format("%.2f", FrontRight));
            telemetry.addData("b right pwr", "back right pwr: " + String.format("%.2f", BackRight));
            telemetry.addData("b left pwr", "back left pwr: " + String.format("%.2f", BackLeft));

        if (gamepad2.y) {
            // move to 0 degrees.
            robot.platformServo.setPosition(0);
        } else if (gamepad2.x) {
            // move to 90 degrees.
            robot.platformServo.setPosition(0.5);
        } else if (gamepad2.a) {
            // move to 180 degrees.
            robot.platformServo.setPosition(1);
            telemetry.addData("Platform Servo Position", robot.platformServo.getPosition());
            telemetry.addData("Platform Servo Position", robot.platformServo.getPosition());
            telemetry.addData("Status", "Running");
            telemetry.update();

        }

    }
}