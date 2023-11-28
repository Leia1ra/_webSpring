package com.multi.campus.mapper;

import com.multi.campus.vo.DataVO;
import com.multi.campus.vo.DatafileVO;

import java.util.List;

public interface DataMapper {
    public List<DataVO> dataList();
    public int dataInsert(DataVO vo);
    public int dataFileInsert(List<DatafileVO> list);
    public void dataHitCount(int no);
    public DataVO dataSelect(int no);
    public List<DatafileVO> getDataFile(int no);
    public int dataUpdate(DataVO vo);
    public int dataDelete(int no);
    public int dataRecordDelete(int no, String userid);

}
