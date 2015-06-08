/*
 * Copyright 2007-2107 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.ymate.platform.webmvc.handle;

import net.ymate.platform.core.YMP;
import net.ymate.platform.core.beans.BeanMeta;
import net.ymate.platform.core.beans.IBeanHandler;
import net.ymate.platform.webmvc.IWebMvc;
import net.ymate.platform.webmvc.annotation.Controller;

/**
 * 控制器类处理器
 *
 * @author 刘镇 (suninformation@163.com) on 15/5/27 上午2:20
 * @version 1.0
 */
public class ControllerHandler implements IBeanHandler {

    private YMP __owner;

    private IWebMvc __webMvc;

    public ControllerHandler(YMP owner, IWebMvc webMvc) {
        __owner = owner;
        __webMvc = webMvc;
    }

    @SuppressWarnings("unchecked")
    public Object handle(Class<?> targetClass) throws Exception {
        if (targetClass.isAnnotationPresent(Controller.class)) {
            if (__webMvc.registerController((Class<? extends Controller>) targetClass)) {
                if (!targetClass.getAnnotation(Controller.class).singleton()) {
                    return targetClass.newInstance();
                } else {
                    __owner.registerBean(BeanMeta.create(targetClass));
                }
            }
        }
        return null;
    }
}