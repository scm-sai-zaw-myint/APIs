package com.scm.api.persistence.dao.impl;

import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.scm.api.persistence.dao.SpreadSheetDAO;
import com.scm.api.persistence.entity.SpreadSheet;

@Transactional
public class SpreadSheetDAOImpl implements SpreadSheetDAO{

    private static final String SELECT="SELECT sheet FROM SpreadSheet sheet ";
    
    @Autowired
    private SessionFactory sessionFactory;
    
    @SuppressWarnings("unchecked")
    @Override
    public List<SpreadSheet> getSpreadSheets() {
        StringBuffer sql = new StringBuffer(SELECT);
        Query query = this.sessionFactory.getCurrentSession().createQuery(sql.toString());
        return (List<SpreadSheet>)query.getResultList();
    }

    @Override
    public SpreadSheet getSpreadSheetById(Integer Id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean createSpreadSheet(SpreadSheet sheet) {
        this.sessionFactory.getCurrentSession().save(sheet);
        return true;
    }

    @Override
    public boolean updateSpreadSheet(SpreadSheet sheet) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean deleteSpreadSheet(Integer Id) {
        // TODO Auto-generated method stub
        return false;
    }

}
