package org.ogre4j.examples;

import org.ogre4j.Camera;
import org.ogre4j.Common;
import org.ogre4j.EventProcessor;
import org.ogre4j.FrameListener;
import org.ogre4j.FrameStats;
import org.ogre4j.InputReader;
import org.ogre4j.KeyEvent;
import org.ogre4j.KeyListener;
import org.ogre4j.MaterialManager;
import org.ogre4j.Overlay;
import org.ogre4j.OverlayElement;
import org.ogre4j.OverlayManager;
import org.ogre4j.PlatformManager;
import org.ogre4j.RenderWindow;
import org.ogre4j.Vector3;

class ExampleFrameListener extends FrameListener implements KeyListener {

    protected int mAniso;

    protected Camera mCamera;

    protected Overlay mDebugOverlay;

    protected EventProcessor mEventProcessor;

    protected int mFiltering;

    protected InputReader mInputDevice;

    protected float mMoveScale;

    protected float mMoveSpeed;

    protected int mNumScreenShots;

    protected float mRotateSpeed;

    protected float mRotScale;

    protected float mRotX, mRotY;

    protected int mSceneDetailIndex;

    protected boolean mStatsOn;

    // just to stop toggles flipping too fast
    protected float mTimeUntilNextToggle;

    protected Vector3 mTranslateVector;

    protected boolean mUseBufferedInputKeys, mUseBufferedInputMouse,
            mInputTypeSwitchingOn;

    protected RenderWindow mWindow;

    public ExampleFrameListener(RenderWindow window, Camera camera) {
        this(window, camera, false);
    }

    public ExampleFrameListener(RenderWindow window, Camera camera,
            boolean useBufferedInputKeys) {
        this(window, camera, useBufferedInputKeys, false);
    }

    public ExampleFrameListener(RenderWindow mWindow, Camera mCamera,
            boolean useBufferedInputKeys, boolean useBufferedInputMouse) {
        mDebugOverlay = OverlayManager.getSingleton().getByName(
                "Core/DebugOverlay");

        this.mUseBufferedInputKeys = useBufferedInputKeys;
        this.mUseBufferedInputMouse = useBufferedInputMouse;
        this.mInputTypeSwitchingOn = useBufferedInputKeys
                || useBufferedInputMouse;
        mRotateSpeed = 36;
        mMoveSpeed = 100;

        if (mInputTypeSwitchingOn) {
            mEventProcessor = new EventProcessor();
            mEventProcessor.initialise(mWindow);
            mEventProcessor.startProcessingEvents();
            mEventProcessor.addKeyListener(this);
            mInputDevice = mEventProcessor.getInputReader();

        } else {
            mInputDevice = PlatformManager.getSingleton().createInputReader();
            mInputDevice.initialise(mWindow, true, true);
        }

        this.mCamera = mCamera;
        this.mWindow = mWindow;
        mStatsOn = true;
        mNumScreenShots = 0;
        mTimeUntilNextToggle = 0;
        mSceneDetailIndex = 0;
        mMoveScale = 0.0f;
        mRotScale = 0.0f;
        mTranslateVector = Vector3.ZERO;
        mAniso = 1;
        mFiltering = Common.TFO_BILINEAR;

        showDebugOverlay(true);
    }

    public boolean frameEnded(float timeSinceLastEvent, float timeSinceLastFrame) {
        updateStats();
        return true;
    }

    public boolean frameStarted(float timeSinceLastEvent,
            float timeSinceLastFrame) {
        if (mWindow.isClosed()) {
            return false;
        }

        if (!mInputTypeSwitchingOn) {
            mInputDevice.capture();
        }

        if (!mUseBufferedInputMouse || !mUseBufferedInputKeys) {
            // one of the input modes is immediate, so setup what is needed for
            // immediate mouse/key movement
            if (mTimeUntilNextToggle >= 0)
                mTimeUntilNextToggle -= timeSinceLastFrame;

            // If this is the first frame, pick a speed
            if (timeSinceLastFrame == 0) {
                mMoveScale = 1;
                mRotScale = 0.1f;
            }
            // Otherwise scale movement units by time passed since last frame
            else {
                // Move about 100 units per second,
                mMoveScale = mMoveSpeed * timeSinceLastFrame;
                // Take about 10 seconds for full rotation
                mRotScale = mRotateSpeed * timeSinceLastFrame;
            }
            mRotX = 0;
            mRotY = 0;
            mTranslateVector = Vector3.ZERO;
        }

        if (mUseBufferedInputKeys) {
            // no need to do any processing here, it is handled by event
            // processor and
            // you get the results as KeyEvents
        } else {
            if (processUnbufferedKeyInput(timeSinceLastEvent,
                    timeSinceLastFrame) == false) {
                return false;
            }
        }
        if (mUseBufferedInputMouse) {
            // no need to do any processing here, it is handled by event
            // processor and
            // you get the results as MouseEvents
        } else {
            if (processUnbufferedMouseInput(timeSinceLastEvent,
                    timeSinceLastFrame) == false) {
                return false;
            }
        }

        if (!mUseBufferedInputMouse || !mUseBufferedInputKeys) {
            // one of the input modes is immediate, so update the movement
            // vector

            moveCamera();

        }

        return true;
    }

    public void keyClicked(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    public void keyFocusIn(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    private boolean displayCameraDetails = false;

    public void keyFocusOut(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    private void moveCamera() {

        // Make all the changes to the camera
        // Note that YAW direction is around a fixed axis (freelook style)
        // rather than a natural YAW (e.g. airplane)
        mCamera.yaw(mRotX);
        mCamera.pitch(mRotY);
        mCamera.moveRelative(mTranslateVector);
    }

    private boolean processUnbufferedKeyInput(float timeSinceLastEvent,
            float timeSinceLastFrame) {
        if (mInputDevice.isKeyDown(InputReader.KC_A)) {
            // Move camera left
            mTranslateVector.x = -mMoveScale;
        }

        if (mInputDevice.isKeyDown(InputReader.KC_D)) {
            // Move camera RIGHT
            mTranslateVector.x = mMoveScale;
        }

        /* Move camera forward by keypress. */
        if (mInputDevice.isKeyDown(InputReader.KC_UP)
                || mInputDevice.isKeyDown(InputReader.KC_W)) {
            mTranslateVector.z = -mMoveScale;
        }

        /* Move camera backward by keypress. */
        if (mInputDevice.isKeyDown(InputReader.KC_DOWN)
                || mInputDevice.isKeyDown(InputReader.KC_S)) {
            mTranslateVector.z = mMoveScale;
        }

        if (mInputDevice.isKeyDown(InputReader.KC_PGUP)) {
            // Move camera up
            mTranslateVector.y = mMoveScale;
        }

        if (mInputDevice.isKeyDown(InputReader.KC_PGDOWN)) {
            // Move camera down
            mTranslateVector.y = -mMoveScale;
        }

        if (mInputDevice.isKeyDown(InputReader.KC_RIGHT)) {
            mCamera.yaw(-mRotScale);
        }

        if (mInputDevice.isKeyDown(InputReader.KC_LEFT)) {
            mCamera.yaw(mRotScale);
        }

        if (mInputDevice.isKeyDown(InputReader.KC_ESCAPE)) {
            return false;
        }

        // see if switching is on, and you want to toggle
        if (mInputTypeSwitchingOn && mInputDevice.isKeyDown(InputReader.KC_M)
                && mTimeUntilNextToggle <= 0) {
            switchMouseMode();
            mTimeUntilNextToggle = 1;
        }

        if (mInputTypeSwitchingOn && mInputDevice.isKeyDown(InputReader.KC_K)
                && mTimeUntilNextToggle <= 0) {
            // must be going from immediate keyboard to buffered keyboard
            switchKeyMode();
            mTimeUntilNextToggle = 1;
        }
        if (mInputDevice.isKeyDown(InputReader.KC_F)
                && mTimeUntilNextToggle <= 0) {
            mStatsOn = !mStatsOn;
            showDebugOverlay(mStatsOn);

            mTimeUntilNextToggle = 1;
        }
        if (mInputDevice.isKeyDown(InputReader.KC_T)
                && mTimeUntilNextToggle <= 0) {
            switch (mFiltering) {
            case Common.TFO_BILINEAR:
                mFiltering = Common.TFO_TRILINEAR;
                mAniso = 1;
                break;
            case Common.TFO_TRILINEAR:
                mFiltering = Common.TFO_ANISOTROPIC;
                mAniso = 8;
                break;
            case Common.TFO_ANISOTROPIC:
                mFiltering = Common.TFO_BILINEAR;
                mAniso = 1;
                break;
            default:
                break;
            }
            MaterialManager.getSingleton().setDefaultTextureFiltering(
                    mFiltering);
            MaterialManager.getSingleton().setDefaultAnisotropy(mAniso);

            showDebugOverlay(mStatsOn);

            mTimeUntilNextToggle = 1;
        }

        if (mInputDevice.isKeyDown(InputReader.KC_SYSRQ)
                && mTimeUntilNextToggle <= 0) {
            String tmp = "screenshot_" + ++mNumScreenShots + ".png";
            mWindow.writeContentsToFile(tmp);
            mTimeUntilNextToggle = 0.5f;
            mWindow.setDebugText("Wrote " + tmp);
        }

        if (mInputDevice.isKeyDown(InputReader.KC_R)
                && mTimeUntilNextToggle <= 0) {
            mSceneDetailIndex = (mSceneDetailIndex + 1) % 3;
            switch (mSceneDetailIndex) {
            case 0:
                mCamera.setDetailLevel(Common.SDL_SOLID);
                break;
            case 1:
                mCamera.setDetailLevel(Common.SDL_WIREFRAME);
                break;
            case 2:
                mCamera.setDetailLevel(Common.SDL_POINTS);
                break;
            }
            mTimeUntilNextToggle = 0.5f;
        }

        if (mInputDevice.isKeyDown(InputReader.KC_P)
                && mTimeUntilNextToggle <= 0) {
            displayCameraDetails = !displayCameraDetails;
            mTimeUntilNextToggle = 0.5f;
            if (!displayCameraDetails)
                mWindow.setDebugText("");
        }
        if (displayCameraDetails) {
            // Print camera details
            mWindow.setDebugText("P: " + mCamera.getDerivedPosition() + " "
                    + "O: " + mCamera.getDerivedOrientation());
        }

        // Return true to continue rendering
        return true;
    }

    private void switchMouseMode() {
        // TODO Auto-generated method stub

    }

    private void switchKeyMode() {
        // TODO Auto-generated method stub

    }

    private boolean processUnbufferedMouseInput(float timeSinceLastEvent,
            float timeSinceLastFrame) {
        /*
         * Rotation factors, may not be used if the second mouse button is
         * pressed.
         */

        /*
         * If the second mouse button is pressed, then the mouse movement
         * results in sliding the camera, otherwise we rotate.
         */
        if (mInputDevice.getMouseButton((char) 1)) {
            mTranslateVector.x += mInputDevice.getMouseRelativeX() * 0.13;
            mTranslateVector.y -= mInputDevice.getMouseRelativeY() * 0.13;
        } else {

            mRotX = (float) Math
                    .toDegrees(-mInputDevice.getMouseRelativeX() * 0.13);
            mRotY = (float) Math
                    .toDegrees(-mInputDevice.getMouseRelativeY() * 0.13);
        }

        return true;
    }

    public void showDebugOverlay(boolean show) {
        if (mDebugOverlay != null) {
            if (show) {
                mDebugOverlay.show();
            } else {
                mDebugOverlay.hide();
            }
        }
    }

    protected void updateStats() {
        String currFps = "Current FPS: ";
        String avgFps = "Average FPS: ";
        String bestFps = "Best FPS: ";
        String worstFps = "Worst FPS: ";
        String tris = "Triangle Count: ";

        // update stats when necessary

        OverlayElement guiAvg = OverlayManager.getSingleton()
                .getOverlayElement("Core/AverageFps");
        OverlayElement guiCurr = OverlayManager.getSingleton()
                .getOverlayElement("Core/CurrFps");
        OverlayElement guiBest = OverlayManager.getSingleton()
                .getOverlayElement("Core/BestFps");
        OverlayElement guiWorst = OverlayManager.getSingleton()
                .getOverlayElement("Core/WorstFps");

        FrameStats stats = mWindow.getStatistics();

        guiAvg.setCaption(avgFps + stats.avgFPS);
        guiCurr.setCaption(currFps + stats.lastFPS);
        guiBest.setCaption(bestFps + stats.bestFPS + " " + stats.bestFrameTime
                + " ms");
        guiWorst.setCaption(worstFps + stats.worstFPS + " "
                + stats.worstFrameTime + " ms");

        OverlayElement guiTris = OverlayManager.getSingleton()
                .getOverlayElement("Core/NumTris");
        guiTris.setCaption(tris + stats.triangleCount);

        OverlayElement guiDbg = OverlayManager.getSingleton()
                .getOverlayElement("Core/DebugText");
        guiDbg.setCaption(mWindow.getDebugText());

    }

}