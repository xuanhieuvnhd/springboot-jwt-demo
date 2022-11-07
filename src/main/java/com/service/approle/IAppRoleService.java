package com.service.approle;


import com.model.AppRole;
import com.service.IGeneralService;

public interface IAppRoleService extends IGeneralService<AppRole> {
    AppRole findByName(String name);
}
