package com.code.fastframe.ciface;

import java.util.List;

/**
 * Created by dengshaomin on 2017/11/7.
 */

public interface IPermissionActivity {

    List<String> needPermissions();

    void permissionsSuccess();

    void permissionsFail(List<String> permissions);
}
