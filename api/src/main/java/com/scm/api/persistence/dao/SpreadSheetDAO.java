package com.scm.api.persistence.dao;

import java.util.List;

import com.scm.api.persistence.entity.SpreadSheet;

public interface SpreadSheetDAO {
    public List<SpreadSheet> getSpreadSheets();
    public SpreadSheet getSpreadSheetById(Integer Id);
    public boolean createSpreadSheet(SpreadSheet sheet);
    public boolean updateSpreadSheet(SpreadSheet sheet);
    public boolean deleteSpreadSheet(Integer Id);
}
