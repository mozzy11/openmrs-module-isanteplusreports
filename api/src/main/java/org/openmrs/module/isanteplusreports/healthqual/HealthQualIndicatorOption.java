package org.openmrs.module.isanteplusreports.healthqual;

import org.openmrs.module.reporting.common.MessageUtil;

import java.util.ArrayList;
import java.util.List;

public class HealthQualIndicatorOption {
	
	private String id;
	
	private String label;
	
	private List<String> values;
	
	private boolean isSet;
	
	public HealthQualIndicatorOption(List<String> data) {
		this.values = new ArrayList<String>();
		
		if (data != null) {
			isSet = true;
			for (int i = 0; i < data.size(); i++) {
				if (i == 0)
					this.id = data.get(i);
				else
					this.values.add(data.get(i));
			}
			this.label = MessageUtil.translate("isanteplusreports.healthqual.option.label." + this.id);
		} else {
			isSet = false;
		}
	}
	
	public String getId() {
		return id;
	}
	
	public String getLabel() {
		return label;
	}
	
	public List<String> getValues() {
		return values;
	}
	
	public boolean isSet() {
		return isSet;
	}
}
