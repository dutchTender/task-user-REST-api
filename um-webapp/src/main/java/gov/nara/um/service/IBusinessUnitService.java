package gov.nara.um.service;

import gov.nara.common.persistence.service.IService;
import gov.nara.um.persistence.model.BusinessUnit;

public interface IBusinessUnitService extends IService<BusinessUnit> {

    public BusinessUnit addUser(final String unitId, final String userId);

    public BusinessUnit removerUser(final String unitId, final String userId);

}
