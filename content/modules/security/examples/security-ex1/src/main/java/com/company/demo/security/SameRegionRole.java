package com.company.demo.security;

import com.company.demo.security.restrictions.SameRegionCustomersRole;
import com.company.demo.security.restrictions.SameRegionRowsRole;
import io.jmix.security.role.annotation.RowLevelRole;


//tag::combined-role[]
@RowLevelRole(name = "Can see data of their region", code = SameRegionRole.CODE)
public interface SameRegionRole extends SameRegionCustomersRole, SameRegionRowsRole {
    String CODE = "same-region-role";
}

//end::combined-role[]