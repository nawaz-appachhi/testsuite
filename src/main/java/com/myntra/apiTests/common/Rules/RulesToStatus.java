package com.myntra.apiTests.common.Rules;

import com.myntra.apiTests.common.Constants.ReleaseStatus;
import com.myntra.apiTests.common.Constants.ShipmentSource;
import com.myntra.apiTests.common.entries.ReleaseEntry;
import com.myntra.apiTests.common.entries.RulesEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shubham Gupta on 8/5/17.
 */
public class RulesToStatus {

    public List<RulesEntry> getRuleDefault(ReleaseEntry releaseEntry){
        List<RulesEntry> rules = new ArrayList<>();
        rules.addAll(getCommonRules(releaseEntry));
        rules.add(new RulesEntry(8,ReleaseStatus.OFD,"processFromSHToOFD"));
        rules.add(new RulesEntry(9,ReleaseStatus.DL,"processFromOFDtoDL"));
        return rules;
    }

    public List<RulesEntry> getRuleFD(ReleaseEntry releaseEntry){
        List<RulesEntry> rules = new ArrayList<>();
        rules.addAll(getCommonRules(releaseEntry));
        rules.add(new RulesEntry(8,ReleaseStatus.OFD,"processFromSHToOFD"));
        rules.add(new RulesEntry(9,ReleaseStatus.FD,"processFromOFDtoFD"));
        return rules;
    }

    public List<RulesEntry> getRuleFDDL(ReleaseEntry releaseEntry){
        List<RulesEntry> rules = new ArrayList<>();
        rules.addAll(getCommonRules(releaseEntry));
        rules.add(new RulesEntry(8,ReleaseStatus.OFD,"processFromSHToOFD"));
        rules.add(new RulesEntry(9,ReleaseStatus.FDDL,"processFromOFDtoFDDL"));
        return rules;
    }

    public List<RulesEntry> getRuleFDFDDL(ReleaseEntry releaseEntry){
        List<RulesEntry> rules = new ArrayList<>();
        rules.addAll(getCommonRules(releaseEntry));
        rules.add(new RulesEntry(8,ReleaseStatus.OFD,"processFromSHToOFD"));
        rules.add(new RulesEntry(9,ReleaseStatus.FDFDDL,"processFromOFDtoFDFDDL"));
        return rules;
    }

    public List<RulesEntry> getRuleFDTODL(ReleaseEntry releaseEntry){
        List<RulesEntry> rules = new ArrayList<>();
        rules.addAll(getCommonRules(releaseEntry));
        rules.add(new RulesEntry(8,ReleaseStatus.OFD,"processFromSHToOFD"));
        rules.add(new RulesEntry(9,ReleaseStatus.FDTODL,"processFromOFDtoFDTODL"));
        return rules;
    }

    public List<RulesEntry> getRuletoUNRTO(ReleaseEntry releaseEntry){
        List<RulesEntry> rules = new ArrayList<>();
        rules.addAll(getCommonRules(releaseEntry));
        rules.add(new RulesEntry(8,ReleaseStatus.UNRTO,"processFromSHtoRTO"));
        return rules;
    }

    public List<RulesEntry> getRuletoRTO(ReleaseEntry releaseEntry){
        List<RulesEntry> rules = new ArrayList<>();
        rules.addAll(getCommonRules(releaseEntry));
        rules.add(new RulesEntry(8,ReleaseStatus.OFD,"processFromSHToOFD"));
        rules.add(new RulesEntry(9,ReleaseStatus.RTO,"processFromOFDtoFDRTO"));
        return rules;
    }

    public List<RulesEntry> getRuletoSMDL(ReleaseEntry releaseEntry){
        List<RulesEntry> rules = new ArrayList<>();
        rules.addAll(getCommonRules(releaseEntry));
        rules.add(new RulesEntry(8,ReleaseStatus.OFD,"processFromSHToOFD"));
        rules.add(new RulesEntry(9,ReleaseStatus.SMDL,"processFromOFDtoSMDL"));
        return rules;
    }

    public List<RulesEntry> getRuletoLOST(ReleaseEntry releaseEntry){
        List<RulesEntry> rules = new ArrayList<>();
        rules.addAll(getCommonRules(releaseEntry));
        rules.add(new RulesEntry(8,ReleaseStatus.LOST,"processFromSHtoLOST"));
        return rules;
    }

    public List<RulesEntry> getCommonRules(ReleaseEntry releaseEntry){
        List<RulesEntry> rules = new ArrayList<>();
        if (releaseEntry.getShipmentSource()== ShipmentSource.MYNTRA) {
            rules.add(new RulesEntry(1, ReleaseStatus.Q, null));
            rules.add(new RulesEntry(2, ReleaseStatus.WP, "processReleaseToWP"));
            if (releaseEntry.getForce() == true)
                rules.add(new RulesEntry(3, ReleaseStatus.PK, "processReleaseFromWPtoPKMock"));
            else
                rules.add(new RulesEntry(3, ReleaseStatus.PK, "processReleaseFromWPtoPK"));
        }else {
            rules.add(new RulesEntry(3, ReleaseStatus.PK, null));
        }
        rules.add(new RulesEntry(4,ReleaseStatus.IS,"processFromPKToIS"));
        rules.add(new RulesEntry(5,ReleaseStatus.ADDED_TO_MB,"processFromISToAddedToMB"));
        rules.add(new RulesEntry(6,ReleaseStatus.CLOSED,"processFromAddedToMBToClosed"));
        rules.add(new RulesEntry(7,ReleaseStatus.SH,"processFromClosedToSH"));
        return rules;
    }
}
