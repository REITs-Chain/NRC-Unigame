package ww.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;

public class ConfigRule implements InitializingBean {
	
	public class RuleItem {
		private String uri;
		private String value;
		private String uriRegex="";
		
		public String getUri() {
			return uri;
		}
		public void setUri(String uri) {
			this.uri = uri.trim();
			if(this.uri!=null&&!this.uri.isEmpty()){
				this.uriRegex=this.uri.replaceAll("\\*\\*", "[\\\\w_/]@");
				this.uriRegex=this.uriRegex.replaceAll("\\*", "[\\\\w_]@");
				this.uriRegex=this.uriRegex.replaceAll("@", "*");
			}
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value.trim();			
		}
		public String getUriRegex() {
			return uriRegex;
		}
	
	}
	
	protected List<RuleItem> ruleItems;

	public ConfigRule() {
		this.ruleItems=new ArrayList<RuleItem>();
	}
		
	public List<RuleItem> loadRules(){

		return null;
	}

	public List<RuleItem> getRuleItems() {
		return ruleItems;
	}

//	public void setRuleItems(List<RuleItem> ruleItems) {
//		this.ruleItems = ruleItems;
//	}
	public void setRuleItems(List<String> rules) {
		this.ruleItems.clear();
		if(rules!=null){			
			for(int i=0;i<rules.size();i++){
				String str=rules.get(i);
				String[] strs=str.split("=");
				RuleItem ri=new RuleItem();
				ri.setUri(strs[0]);
				ri.setValue(strs[1]);	
				this.ruleItems.add(ri);
			}
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		List<RuleItem> items=loadRules();
		if(items!=null)
			this.ruleItems=items;
		
	}

}
