package oscillator.integrators;

import oscillator.OscillatorParticle;

public class OscillatorGearIntegrator extends OscillatorIntegrator{
    private double previousRVector[];

    @Override
    public void applyIntegrator(OscillatorParticle oscillatorParticle, double deltaT, double elasticConstant, double damping) {
//        if(this.previousRVector == null) {
//            initializeRVector(elasticConstant, damping);
//        }
//
//        // multiplier
//        double matrix[][] = multiplier(deltaT);
//
//        // initialize predicted r
//        double RPredictedVector[] = new double[6];
//
//        // calculates predicted r
//        for(int i=0; i<6; i++) {
//            for(int j=0; j<6; j++) {
//                RPredictedVector[i] += matrix[i][j]*previousRVector[j];
//            }
//        }
//
//        double r2tdeltaT = getActingForces(elasticConstant, damping, RPredictedVector[0], RPredictedVector[1])/mass;
//        double r2ptdeltaT = RPredictedVector[2];
//        double deltaA =r2tdeltaT - r2ptdeltaT; // TODO: check if this should be abs
//
//        // correction
//        double deltaR2 = deltaA * deltaT * deltaT / 2;
//
//        // alphas to correct predicted values
//        double c[] = {3/16.0, 251/360.0, 1, 11/18.0, 1/6.0, 1/60.0};
//
//        // updates x and v
//        x = RPredictedVector[0] + c[0]*deltaR2 * 0 / Math.pow(deltaT, 0);
//        v = RPredictedVector[1] + c[1]*deltaR2 * 1 / Math.pow(deltaT, 1);
//
//        // apply corrections and save them
//        for(int i=0; i<6; i++) {
//            previousRVector[i] = RPredictedVector[i] + c[i]*deltaR2 * i / Math.pow(deltaT, i);
//        }
    }
//
//    private double[][] multiplier(double deltaT) {
//        double matrix[][] = {
//                {1, deltaT, Math.pow(deltaT,2)/2, Math.pow(deltaT,3)/6, Math.pow(deltaT,4)/24, Math.pow(deltaT,5)/120},
//                {0, 1, deltaT, Math.pow(deltaT,2)/2, Math.pow(deltaT,3)/6, Math.pow(deltaT,4)/24},
//                {0, 0, 1, deltaT, Math.pow(deltaT,2)/2, Math.pow(deltaT,3)/6},
//                {0, 0, 0, 1, deltaT, Math.pow(deltaT,2)/2},
//                {0, 0, 0, 0, 1, deltaT},
//                {0, 0, 0, 0, 0, 1}};
//        return matrix;
//    }
//
//    private void initializeRVector(double elasticConstant, double damping) {
//        this.previousRVector = new double[6];
//        this.previousRVector[0] = x;
//        this.previousRVector[1] = v;
//        this.previousRVector[2] = getActingForces(elasticConstant, damping)/mass;
//        this.previousRVector[3] = 0;
//        this.previousRVector[4] = 0;
//        this.previousRVector[5] = 0;
//    }
}
