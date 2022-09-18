package com.mycompany.business;

import java.util.ArrayList;

import java.util.List;

import com.mycompany.domain.Org;

public class OrgManager {
	private List  list;
	public OrgManager(){
		int count= 87;
		list = new ArrayList();
		for(int i=0;i<count;i++){
			Org org = new Org();
			org.setId(i);
			org.setName("Name_"+i);
			list.add(org);
		}
	}

	public List getOrgs(int begin, int length,String sort,String dir){
		if(dir==null||dir.equals("ASC"))
		return getOrgs(begin,length);
		int first =list.size()-begin-1;
		List retList = new ArrayList();
		while(length>0&&first>=0){
			retList.add(list.get(first));
			length--;
			first--;
		}
		return retList;
	}
	public List getOrgs(int begin, int length) {
		return list.subList(begin, begin+length<=list.size()-1?begin+length:list.size());
	}
	public Org getOrg(int id){
		for(int i=0;i<list.size();i++){
			Org org = (Org)list.get(i);
			if(org.getId()==id) return org;
		}
		return null;
	}
	public void add(Org org) {
		if(org==null) return;
		if(getOrg(org.getId())!=null) return;
		list.add(org);
	}
	public void remove(int orgId){
		Org org = getOrg(orgId);
		if(org!=null) list.remove(org);
	}
	public int getCount() {
		return list.size();
	}
}
