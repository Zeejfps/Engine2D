package com.zeejfps.engine2d;

import com.zeejfps.engine2d.util.Keyboard;
import com.zeejfps.engine2d.util.Mouse;

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
