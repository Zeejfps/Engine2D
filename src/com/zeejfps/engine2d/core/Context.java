package com.zeejfps.engine2d.core;

import com.zeejfps.engine2d.core.util.Keyboard;
import com.zeejfps.engine2d.core.util.Mouse;

/**
 * Created by Zeejfps on 1/1/14.
 */
public class Context {

    public final Keyboard keyboard;
    public final Mouse mouse;

    public Context() {

        keyboard = new Keyboard();
        mouse = new Mouse();

    }

}
