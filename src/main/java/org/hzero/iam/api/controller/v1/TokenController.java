package org.hzero.iam.api.controller.v1;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.swagger.annotation.Permission;

import org.hzero.core.util.Results;
import org.hzero.iam.config.SwaggerApiConfig;
import org.hzero.iam.infra.feign.OauthAdminFeignClient;

/**
 * 失效token Controller
 *
 * @author xiaoyu.zhao@hand-china.com 2019/11/08 15:40
 */
@Api(tags = SwaggerApiConfig.TOKEN_MANAGER)
@RestController("tokenController.v1")
@RequestMapping("/v1/{organizationId}/tokens")
public class TokenController {

    @Autowired
    private OauthAdminFeignClient oauthAdminFeignClient;

    @ApiOperation("根据用户名失效Token")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @DeleteMapping("/username")
    public ResponseEntity invalidByUsername(@PathVariable Long organizationId, @RequestParam("loginName") String loginName){
        oauthAdminFeignClient.invalidByUsername(loginName);
        return Results.success();
    }

    @ApiOperation("根据令牌失效Token")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @DeleteMapping("/one")
    public ResponseEntity invalidByToken(@PathVariable Long organizationId, @RequestParam("token") String token){
        oauthAdminFeignClient.invalidByToken(token);
        return Results.success();
    }

    @ApiOperation("根据令牌批量失效Token")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @DeleteMapping("/batch")
    public ResponseEntity invalidByTokenBatch(@PathVariable Long organizationId, @RequestParam("tokens") List<String> tokens){
        oauthAdminFeignClient.invalidByTokenBatch(tokens);
        return Results.success();
    }
}
