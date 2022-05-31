package hellgate.common.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 修改密码表单。
 * <p>
 * 此表单用于修改密码，需要提供旧密码验证是否具备修改权限。
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ModifyPasswordForm extends ConfirmPasswordForm {

    /**
     * 旧密码。
     * <p>
     * 用于验证是否具备修改权限，与 password 字段的校验规则及要求完全相同。
     */
    @NotBlank
    @Size(min = 6, max = 15)
    @ToString.Exclude
    @JsonIgnore
    private String oldPassword;
}
