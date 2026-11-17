package com.example.demo;
import java.lang.Math;
public class ProjectileMethods {
    double initial_velocity = 0;
    double angle = 0;
    double length = 0;
    double height = 0;
    double flight_time = 0;
    static final double gravity = 9.81;
    public ProjectileMethods(double initial_velocity, double angle) {
        this.initial_velocity = initial_velocity;
        this.angle = Math.toRadians(angle);
    }
    public double getLength() {
        return (Math.pow(this.initial_velocity, 2) * Math.sin(2 * angle)) / gravity;
    }
    public double getHeight() {
        return (Math.pow(this.initial_velocity, 2) * Math.pow(Math.sin(angle), 2)) / (2 * gravity);
    }
    public double getFlight_time() {
        return ((2 * this.initial_velocity) * Math.sin(this.angle)) / gravity;
    }
    public double trajectoryFunction(double x) {
        // oh dear
        return (x * Math.tan(angle)) -
                (gravity * Math.pow(x, 2)) /
                        (2 * Math.pow(initial_velocity, 2) *
                                Math.pow(Math.cos(angle), 2));
        // you're telling me my problem WAS MY FUCKING TRAJCECTORY EQUATION.
    }
}
