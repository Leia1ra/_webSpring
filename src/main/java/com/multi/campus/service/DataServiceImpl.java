package com.multi.campus.service;

import com.multi.campus.mapper.DataMapper;
import com.multi.campus.vo.DataVO;
import com.multi.campus.vo.DatafileVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class DataServiceImpl implements DataService{
    @Inject
    DataMapper mapper;

    @Override
    public List<DataVO> dataList() {
        return mapper.dataList();
    }

    @Override
    public int dataInsert(DataVO vo) {
        return mapper.dataInsert(vo);
    }

    @Override
    public int dataFileInsert(List<DatafileVO> list) {
        return mapper.dataFileInsert(list);
    }

    @Override
    public void dataHitCount(int no) {
        mapper.dataHitCount(no);
    }

    @Override
    public DataVO dataSelect(int no) {
        return mapper.dataSelect(no);
    }

    @Override
    public List<DatafileVO> getDataFile(int no) {
        return mapper.getDataFile(no);
    }

    @Override
    public int dataUpdate(DataVO vo) {
        return mapper.dataUpdate(vo);
    }

    @Override
    public int dataDelete(int no) {
        return mapper.dataDelete(no);
    }

    @Override
    public int dataRecordDelete(int no, String userid) {
        return mapper.dataRecordDelete(no,userid);
    }
}
