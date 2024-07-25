package com.liaimei.supercodeengine.camelwords

import com.intellij.openapi.application.ApplicationActivationListener
import com.intellij.openapi.editor.ex.EditorSettingsExternalizable
import com.intellij.openapi.wm.IdeFrame
import java.awt.KeyEventDispatcher
import java.awt.KeyboardFocusManager
import java.awt.event.KeyEvent
import java.util.*

/**
 * 监听IDE应用程序激活状态的类，用于在特定的键盘快捷键被按下时，切换编辑器的驼峰式单词选择设置。
 */
class CamelWordsListener : ApplicationActivationListener {
    // 定义一个KeyEventDispatcher来处理特定的键盘事件。
    // 键盘事件调度器，用于处理特定的键盘快捷键组合。
    private val dispatcher: KeyEventDispatcher = MyKeyEventDispatcher()

    /**
     * 当应用程序被激活时，注册键盘事件调度器。
     * @param ideFrame IDE的框架引用，此处未使用。
     */
    override fun applicationActivated(ideFrame: IdeFrame) {
        // 在应用程序激活时，添加键盘事件调度器以监听键盘事件。
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(dispatcher)
    }

    /**
     * 当应用程序被停用时，取消注册键盘事件调度器。
     * @param ideFrame IDE的框架引用，此处未使用。
     */
    override fun applicationDeactivated(ideFrame: IdeFrame) {
        // 在应用程序停用时，移除键盘事件调度器。
        KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(dispatcher)
    }

    /**
     * 自定义的KeyEventDispatcher类，用于处理键盘快捷键以切换驼峰式单词选择设置。
     */
    private class MyKeyEventDispatcher : KeyEventDispatcher {
        // 记录Shift键的状态。
        private var shiftPressed = false
        // 记录Ctrl或Meta键（Mac上的Command键）的状态。
        private var controlOrMetaPressed = false
        // 记录J键的状态。
        private var jPressed = false

        // 获取编辑器设置的实例，用于切换驼峰式单词选择状态。
        private val editorSettingsExternalizable: EditorSettingsExternalizable =
            EditorSettingsExternalizable.getInstance()

        /**
         * 处理键盘事件。
         * @param event 键盘事件对象。
         * @return 是否处理了该事件。
         */
        override fun dispatchKeyEvent(event: KeyEvent): Boolean {
            // 判断当前操作系统是否为MacOS，以处理不同平台的键盘快捷键差异。
            val isMacOS = System.getProperty("os.name").lowercase(Locale.getDefault()).contains("mac")

            val keyCode = event.keyCode
            val id = event.id

            // 当键盘被按下时，检查是否按下了Shift、Ctrl/Meta（取决于操作系统）和J键。
            if (id == KeyEvent.KEY_PRESSED) {
                when (keyCode) {
                    KeyEvent.VK_SHIFT -> shiftPressed = true
                    if (isMacOS) KeyEvent.VK_META else KeyEvent.VK_CONTROL -> controlOrMetaPressed = true
                    KeyEvent.VK_J -> jPressed = true
                }
                // 当Shift、Ctrl/Meta和J键同时被按下时，切换驼峰式单词选择状态。
                if ((shiftPressed && controlOrMetaPressed && jPressed)) {
                    // 切换驼峰式单词选择设置。
                    editorSettingsExternalizable.isCamelWords = !editorSettingsExternalizable.isCamelWords
                    // 重置按键状态。
                    shiftPressed = false
                    controlOrMetaPressed = false
                    jPressed = false
                }
            }
            // 返回false表示未处理事件，允许事件继续传播。
            return false
        }
    }
}
