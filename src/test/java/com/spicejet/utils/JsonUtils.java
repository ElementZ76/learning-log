package com.spicejet.utils;

import java.io.*;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spicejet.models.SearchInfo;

public class JsonUtils {
	public static List<SearchInfo> getSearchInfo(String jsonFileName) throws IOException {
		String filePath = System.getProperty("user.dir") + "/src/test/resources/testdata/" + jsonFileName;
		ObjectMapper objectMapper = new ObjectMapper();		
		return objectMapper.readValue(new File(filePath), new TypeReference<List<SearchInfo>>(){});
	}
}
