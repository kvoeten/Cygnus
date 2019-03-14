/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import com.artemis.*;

/**
 * Wraps artimis ecs world manager
 *
 * @author Kaz
 */
public class Engine extends Thread {

    private static Engine pInstance;
    private static int nInitAttempts;

    private final WorldConfiguration pConfig;
    private final World pWorld;
    private boolean bStarted = false;
    public long nFrameRate = 0;

    @SafeVarargs
    public Engine(Class<? extends BaseSystem>... classes) {
        BaseSystem[] aSystems = new BaseSystem[classes.length];
        for (int i = 0; i < aSystems.length; ++i) {
            try {
                aSystems[i] = classes[i].getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                System.out.println("Failed to initialize Engine Systems.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        pConfig = new WorldConfigurationBuilder().with(aSystems).build();
        pWorld = new World(pConfig);
        pInstance = this;
    }
    
    public static boolean Initialize() {
        if (pInstance == null) {
            if (nInitAttempts > 5) {
                return false;
            }
            pInstance = new Engine();
            pInstance.start();
            nInitAttempts++;
            return Initialize();
        }
        return true;
    }
    
    public static synchronized int CreateEntity() {
        return pInstance.pWorld.create();
    }
    
    public static void RemoveEntity(int nEntityID) {
        pInstance.pWorld.delete(nEntityID);
    }
    
    public static long GetFrameRate() {
        return pInstance.nFrameRate;
    }

    @Override
    public void start() {
        final int TARGET_FPS = 60;
        final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
        long tLastLoop = System.nanoTime(), tPrevFPS = 0;

        while (true) {
            long tNow = System.nanoTime(), tUpdate = tNow - tLastLoop;
            float delta = (tUpdate / ((float) OPTIMAL_TIME));
            
            tLastLoop = tNow;
            tPrevFPS += tUpdate;
            nFrameRate++;

            pWorld.setDelta(delta);
            try {
                pWorld.process();
            } catch (Exception e) {
                e.printStackTrace();
            }

            bStarted = true;
            if (tPrevFPS >= 1000000000) {
                tPrevFPS = 0;
                nFrameRate = 0;
            }

            //If the loop was finished quicker than 10ms sleep the remainder.
            long tRemain = (tLastLoop - System.nanoTime() + OPTIMAL_TIME) / 1000000;
            if (tRemain > 0) {
                try {
                    Thread.sleep(tRemain);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
