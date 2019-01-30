package com.example.mail_project.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;


@Data
@Entity
@NoArgsConstructor 
@AllArgsConstructor
@EqualsAndHashCode(of={"id"})
public class MasterData {
	

	
	private @Id @GeneratedValue(strategy=GenerationType.TABLE) Long id;
	private @Version @JsonIgnore Long version;
	private String createdBy;
	private @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")Timestamp createdDate;
	private String updateBy;
	private @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")Timestamp updateDate;
	
	private String code;
	private String name;
	private String nameMessageCode;
	private String description;
	private Boolean flagActive;
	private Boolean flagOrgCode;
	private Integer numberOfColumn;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "masterdata")
    private Set<MasterDataDetail> details = new HashSet<MasterDataDetail>();
	
	private String variableName1;
	private String variableName2;
	private String variableName3;
	private String variableName4;
	private String variableName5;
	private String variableName6;
	private String variableName7;
	private String variableName8;
	private String variableName9;
	private String variableName10;
	private String variableName11;
	private String variableName12;
	private String variableName13;
	private String variableName14;
	private String variableName15;
	private String variableName16;
	private String variableName17;
	private String variableName18;
	private String variableName19;
	private String variableName20;
	private String variableName21;
	private String variableName22;
	private String variableName23;
	private String variableName24;
	private String variableName25;
	private String variableName26;
	private String variableName27;
	private String variableName28;
	private String variableName29;
	private String variableName30;
	private String variableName41;
	private String variableName42;
	private String variableName43;
	private String variableName44;
	private String variableName45;
	private String variableName46;
	private String variableName47;
	private String variableName48;
	private String variableName49;
	private String variableName50;
	
	private String variableType1;
	private String variableType2;
	private String variableType3;
	private String variableType4;
	private String variableType5;
	private String variableType6;
	private String variableType7;
	private String variableType8;
	private String variableType9;
	private String variableType10;
	private String variableType11;
	private String variableType12;
	private String variableType13;
	private String variableType14;
	private String variableType15;
	private String variableType16;
	private String variableType17;
	private String variableType18;
	private String variableType19;
	private String variableType20;
	private String variableType21;
	private String variableType22;
	private String variableType23;
	private String variableType24;
	private String variableType25;
	private String variableType26;
	private String variableType27;
	private String variableType28;
	private String variableType29;
	private String variableType30;
	private String variableType41;
	private String variableType42;
	private String variableType43;
	private String variableType44;
	private String variableType45;
	private String variableType46;
	private String variableType47;
	private String variableType48;
	private String variableType49;
	private String variableType50;
	
	private Boolean variableFlagRequire1;
	private Boolean variableFlagRequire2;
	private Boolean variableFlagRequire3;
	private Boolean variableFlagRequire4;
	private Boolean variableFlagRequire5;
	private Boolean variableFlagRequire6;
	private Boolean variableFlagRequire7;
	private Boolean variableFlagRequire8;
	private Boolean variableFlagRequire9;
	private Boolean variableFlagRequire10;
	private Boolean variableFlagRequire11;
	private Boolean variableFlagRequire12;
	private Boolean variableFlagRequire13;
	private Boolean variableFlagRequire14;
	private Boolean variableFlagRequire15;
	private Boolean variableFlagRequire16;
	private Boolean variableFlagRequire17;
	private Boolean variableFlagRequire18;
	private Boolean variableFlagRequire19;
	private Boolean variableFlagRequire20;
	private Boolean variableFlagRequire21;
	private Boolean variableFlagRequire22;
	private Boolean variableFlagRequire23;
	private Boolean variableFlagRequire24;
	private Boolean variableFlagRequire25;
	private Boolean variableFlagRequire26;
	private Boolean variableFlagRequire27;
	private Boolean variableFlagRequire28;
	private Boolean variableFlagRequire29;
	private Boolean variableFlagRequire30;
	private Boolean variableFlagRequire41;
	private Boolean variableFlagRequire42;
	private Boolean variableFlagRequire43;
	private Boolean variableFlagRequire44;
	private Boolean variableFlagRequire45;
	private Boolean variableFlagRequire46;
	private Boolean variableFlagRequire47;
	private Boolean variableFlagRequire48;
	private Boolean variableFlagRequire49;
	private Boolean variableFlagRequire50;
	
	private String variableDefaultValue1;
	private String variableDefaultValue2;
	private String variableDefaultValue3;
	private String variableDefaultValue4;
	private String variableDefaultValue5;
	private String variableDefaultValue6;
	private String variableDefaultValue7;
	private String variableDefaultValue8;
	private String variableDefaultValue9;
	private String variableDefaultValue10;
	private String variableDefaultValue11;
	private String variableDefaultValue12;
	private String variableDefaultValue13;
	private String variableDefaultValue14;
	private String variableDefaultValue15;
	private String variableDefaultValue16;
	private String variableDefaultValue17;
	private String variableDefaultValue18;
	private String variableDefaultValue19;
	private String variableDefaultValue20;
	private String variableDefaultValue21;
	private String variableDefaultValue22;
	private String variableDefaultValue23;
	private String variableDefaultValue24;
	private String variableDefaultValue25;
	private String variableDefaultValue26;
	private String variableDefaultValue27;
	private String variableDefaultValue28;
	private String variableDefaultValue29;
	private String variableDefaultValue30;
	private String variableDefaultValue41;
	private String variableDefaultValue42;
	private String variableDefaultValue43;
	private String variableDefaultValue44;
	private String variableDefaultValue45;
	private String variableDefaultValue46;
	private String variableDefaultValue47;
	private String variableDefaultValue48;
	private String variableDefaultValue49;
	private String variableDefaultValue50;
}
