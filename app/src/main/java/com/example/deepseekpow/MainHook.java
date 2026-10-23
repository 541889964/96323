package com.example.deepseekpow;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class MainHook implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
        if (!lpparam.packageName.equals("com.deepseek.chat")) return;

        XposedBridge.log(">>> DeepSeek Pow Hook 终极版已注入 <<<");
        try {
            Class<?> c = XposedHelpers.findClass("com.deepseek.crypto.PowCalculator", lpparam.classLoader);
            
            // 打印类中所有方法，方便排查
            XposedBridge.log("--- 开始打印 PowCalculator 内的方法 ---");
            for (java.lang.reflect.Method m : c.getDeclaredMethods()) {
                StringBuilder sb = new StringBuilder(m.getName()).append("(");
                for (Class<?> param : m.getParameterTypes()) {
                    sb.append(param.getSimpleName()).append(", ");
                }
                sb.append(")");
                XposedBridge.log("发现方法: " + sb.toString());
            }

            // 放弃精确匹配，全量 Hook 同名方法
            XposedBridge.hookAllMethods(c, "calculateDeepSeekHashV1Pow", new XC_MethodHook() {
                @Override 
                protected void beforeHookedMethod(MethodHookParam p) {
                    XposedBridge.log("--- 触发 PoW 计算 ---");
                    for (int i = 0; i < p.args.length; i++) {
                        XposedBridge.log("参数[" + i + "]: " + p.args[i]);
                    }
                }
                @Override 
                protected void afterHookedMethod(MethodHookParam p) {
                    XposedBridge.log("PoW 计算结果: " + p.getResult());
                }
            });
        } catch (Throwable t) { 
            XposedBridge.log("Hook 错误: " + t.getMessage()); 
        }
    }
}
