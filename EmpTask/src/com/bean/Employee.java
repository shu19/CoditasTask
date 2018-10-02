package com.bean;
import java.sql.Date;
public class Employee {

	private String EmpId;
	private String Name;
	private String Manager;
	private String Date;
	private String InTime;
	private String OutTime;
	private String TotalTime;
	private String Status;
	public String getEmpId() {
		return EmpId;
	}
	public void setEmpId(String empId) {
		EmpId = empId;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getManager() {
		return Manager;
	}
	public void setManager(String manager) {
		Manager = manager;
	}
	public String getDate() {
		return Date;
	}
	public void setDate(String date) {
		Date = date;
	}
	public String getInTime() {
		return InTime;
	}
	public void setInTime(String inTime) {
		InTime = inTime;
	}
	public String getOutTime() {
		return OutTime;
	}
	public void setOutTime(String outTime) {
		OutTime = outTime;
	}
	public String getTotalTime() {
		return TotalTime;
	}
	public void setTotalTime(String totalTime) {
		TotalTime = totalTime;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	
}
