package io.renren.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import io.renren.entity.MsgEntity;

public interface ParseExcelService {

	ArrayList<MsgEntity> getMsgListFromExcel(File targetFile) throws IOException;

}