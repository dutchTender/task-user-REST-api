package gov.nara.um.service.impl;

import gov.nara.common.persistence.service.AbstractLongIdService;
import gov.nara.um.persistence.dao.IBUnitConfigurationDAO;
import gov.nara.um.persistence.model.BusinessUnitConfiguration;
import gov.nara.um.service.IBUnitConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class BUnitConfigurationService extends AbstractLongIdService<BusinessUnitConfiguration> implements IBUnitConfigurationService {

    @Autowired
    private IBUnitConfigurationDAO dao;
    BUnitConfigurationService(){super();}

    @Override
    protected JpaSpecificationExecutor<BusinessUnitConfiguration> getSpecificationExecutor() {
        return this.getSpecificationExecutor();
    }
    @Override
    public BusinessUnitConfiguration findByName(String name) {
        return this.findByName(name);
    }
    @Override
    public BusinessUnitConfiguration findOne(Long id) {
        return this.findOne(id);
    }
    @Override
    public void delete(Long id) {
         this.delete(id);
    }
    @Override
    protected IBUnitConfigurationDAO getDao() {
        return this.dao;
    }
}