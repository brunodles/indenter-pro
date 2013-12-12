/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package identer.listener;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

/**
 *
 * @author bruno
 */
public abstract class KeyboardListener implements NativeKeyListener {

    public static void registerNativeHook() throws NativeHookException {
        GlobalScreen.registerNativeHook();
    }

    public static void unregisterNativeHook() {
        GlobalScreen.unregisterNativeHook();
    }

    public void registerKeylistener() {
        GlobalScreen.getInstance().addNativeKeyListener(this);
    }

    public void unregisterKeylistener() {
        GlobalScreen.getInstance().removeNativeKeyListener(this);
    }

//    @Override
//    public void nativeKeyPressed(NativeKeyEvent nke) {
//        nke.
//    }
//
//    @Override
//    public void nativeKeyReleased(NativeKeyEvent nke) {
//    }
//
//    @Override
//    public void nativeKeyTyped(NativeKeyEvent nke) {
//    }
}
