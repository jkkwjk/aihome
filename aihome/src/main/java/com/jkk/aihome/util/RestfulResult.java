package com.jkk.aihome.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jkk.aihome.mm.RestfulResultCode;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestfulResult {
	private RestfulResultCode code;
	private String msg;
	private Object data;

	private RestfulResult() {

	}

	public static RestfulResult success(Object data){
		RestfulResult restfulResult = RestfulResult.success();
		restfulResult.setData(data);

		return restfulResult;
	}

	public static RestfulResult success(){
		RestfulResult restfulResult = new RestfulResult();
		restfulResult.setCode(RestfulResultCode.SUCCESS);

		return restfulResult;
	}

	public static RestfulResult fail(String msg){
		RestfulResult restfulResult = RestfulResult.fail();
		restfulResult.setMsg(msg);

		return restfulResult;
	}

	public static RestfulResult fail(){
		RestfulResult restfulResult = new RestfulResult();
		restfulResult.setCode(RestfulResultCode.FAIL);

		return restfulResult;
	}

	public static RestfulResult restFul(RestfulResultCode code, String msg, Object data) {
		RestfulResult restfulResult = new RestfulResult();
		restfulResult.setMsg(msg);
		restfulResult.setCode(code);
		restfulResult.setData(data);

		return restfulResult;
	}
}
