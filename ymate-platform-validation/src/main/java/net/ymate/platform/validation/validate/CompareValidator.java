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
package net.ymate.platform.validation.validate;

import net.ymate.platform.core.i18n.I18N;
import net.ymate.platform.core.lang.BlurObject;
import net.ymate.platform.validation.IValidator;
import net.ymate.platform.validation.ValidateContext;
import net.ymate.platform.validation.ValidateResult;
import net.ymate.platform.validation.annotation.Validator;
import org.apache.commons.lang.StringUtils;

/**
 * 参数值比较验证
 *
 * @author 刘镇 (suninformation@163.com) on 2013-4-17 下午9:57:16
 * @version 1.0
 */
@Validator(VCompare.class)
public class CompareValidator implements IValidator {

    private static String __COMPARE_VALIDATOR_NOT_EQ = "ymp.validation.compare_validator_not_eq";

    private static String __COMPARE_VALIDATOR_EQ = "ymp.validation.compare_validator_eq";

    public ValidateResult validate(ValidateContext context) {
        if (context.getParamValue() != null && !context.getParamValue().getClass().isArray()) {
            VCompare _vCompare = (VCompare) context.getAnnotation();
            boolean _matched = BlurObject.bind(context.getParamValue()).toStringValue()
                    .equals(BlurObject.bind(context.getParamValues().get(_vCompare.with())).toString());
            String _condStr = "equals";
            switch (_vCompare.cond()) {
                case NOT_EQ:
                    _matched = !_matched;
                    _condStr = "not equals";
                    break;
                case EQ:
            }
            if (!_matched) {
                String _msg = null;
                switch (_vCompare.cond()) {
                    case NOT_EQ:
                        _msg = I18N.formatMessage(VALIDATION_I18N_RESOURCE, __COMPARE_VALIDATOR_NOT_EQ, context.getParamName(), _vCompare.with());
                        break;
                    case EQ:
                        _msg = I18N.formatMessage(VALIDATION_I18N_RESOURCE, __COMPARE_VALIDATOR_EQ, context.getParamName(), _vCompare.with());
                }
                if (StringUtils.trimToNull(_msg) == null) {
                    _msg = I18N.formatMessage("{0} must be {1} {2}", context.getParamName(), _condStr, _vCompare.with());
                }
                return new ValidateResult(context.getParamName(), _msg);
            }
        }
        return null;
    }
}
