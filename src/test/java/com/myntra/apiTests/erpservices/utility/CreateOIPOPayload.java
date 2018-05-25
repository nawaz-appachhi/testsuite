package com.myntra.apiTests.erpservices.utility;

import com.myntra.margincalculator.enums.TaxRegime;
import com.myntra.margincalculator.enums.TaxType;
import com.myntra.purchaseorder.entry.PurchaseOrderEntry;
import com.myntra.purchaseorder.entry.PurchaseOrderSKUEntry;
import com.myntra.purchaseorder.entry.SKUTaxDetailEntry;
import com.myntra.purchaseorder.enums.PurchaseOrderPrioritization;
import com.myntra.purchaseorder.enums.StockOrigin;
import com.myntra.purchaseorder.enums.TaxCriteria;
import com.myntra.vms.client.code.utils.BrandType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Sneha.Agarwal on 20/02/18.
 */

    public class CreateOIPOPayload {
        long[] skuIds = new long[]{4051L, 4052L, 4053L, 4054L, 4055L, 4056L, 4057L, 4058L, 4059L, 4060L, 4061L, 4062L, 4063L, 4064L, 4065L, 4066L, 4067L, 4068L, 4069L, 4070L, 4071L, 4072L, 4073L, 4074L, 4075L, 4076L, 4077L, 4078L, 4079L, 4080L, 4081L, 4082L, 4083L, 4084L, 4085L, 4086L, 4087L, 4088L, 4089L, 4090L, 4091L, 4092L, 4093L, 4094L, 4095L, 4096L, 4097L, 4098L, 4099L, 4100L, 4101L, 4102L, 4103L, 4104L, 4105L, 4106L, 4107L, 4108L, 4110L, 4111L, 4112L, 4113L, 4114L, 4115L, 4116L, 4117L, 4118L, 4119L, 4120L, 4121L, 4122L, 4123L, 4124L, 4125L, 4126L, 4127L, 4128L, 4129L, 4130L, 4131L, 4132L, 4162L, 4163L, 4164L, 4165L, 4166L, 4167L, 4168L, 4169L, 4170L, 4190L, 4191L, 4192L, 4193L, 4194L, 4203L, 4204L, 4205L, 4206L, 4207L, 4208L, 4209L, 4210L, 4211L, 4212L, 4218L, 4219L, 4220L, 4221L, 4232L, 4233L, 4234L, 4235L, 4241L, 4242L, 4243L, 4244L, 4245L, 4246L, 4247L, 4248L, 4253L, 4254L, 4255L, 4256L, 4257L, 4258L, 4259L, 4260L, 4261L, 4262L, 4263L, 4264L, 4265L, 4266L, 4267L, 4268L, 4269L, 4270L, 4271L, 4272L, 4273L, 4275L, 4276L, 4277L, 4285L, 4286L, 4287L, 4288L, 4289L, 4290L, 4291L, 4292L, 4293L, 4294L, 4295L, 4296L, 4297L, 4298L, 4299L, 4300L, 4301L, 4302L, 4303L, 4304L, 4305L, 4306L, 4307L, 4308L, 4309L, 4310L, 4311L, 4312L, 4313L, 4314L, 4315L, 4316L, 4317L, 4318L, 4319L, 4320L, 4321L, 4322L, 4323L, 4324L, 4325L, 4326L, 4327L, 4328L, 4329L, 4330L, 4331L, 4332L, 4333L, 4334L, 4335L, 4336L, 4337L, 4338L, 4339L, 4340L, 4341L, 4342L, 4343L, 4344L, 4345L, 4346L, 4347L, 4348L, 4349L, 4350L, 4351L, 4352L, 4353L, 4354L, 4355L, 4356L, 4357L, 4358L, 4359L, 4360L, 4361L, 4362L, 4363L, 4365L, 4366L, 4393L, 4394L, 4395L, 4396L, 4397L, 4401L, 4402L, 4403L, 4404L, 4407L, 4408L, 4409L, 4410L, 4411L, 4412L, 4413L, 4414L, 4415L, 4416L, 4417L, 4418L, 4419L, 4420L, 4421L, 4422L, 4423L, 4424L, 4425L, 4426L, 4427L, 4428L, 4429L, 4511L, 4512L, 4513L, 4514L, 4515L, 4516L, 4517L, 4518L, 4519L, 4520L, 4521L, 4522L, 4523L, 4524L, 4525L, 4526L, 4527L, 4528L, 6386L, 6387L, 6388L, 6389L, 6390L, 6395L, 6396L, 6397L, 6398L, 6399L, 6403L, 6404L, 6405L, 6406L, 6407L, 6412L, 6413L, 6414L, 6415L, 6416L, 6417L, 6418L, 6419L, 6420L, 6421L, 6422L, 6423L, 6424L, 6425L, 6426L, 6427L, 6428L, 6429L, 6430L, 6431L, 6432L, 6433L, 6434L, 6435L, 6436L, 6437L, 6438L, 6439L, 6440L, 6441L, 6442L, 6443L, 6444L, 6445L, 6446L, 6447L, 6448L, 6449L, 6450L, 6451L, 6452L, 6453L, 6454L, 6455L, 6456L, 6457L, 6458L, 6459L, 6460L, 6461L, 6462L, 6463L, 6464L, 6465L, 6466L, 6467L, 6468L, 6469L, 6470L, 6471L, 6472L, 6473L, 6474L, 6475L, 6476L, 6477L, 6478L, 6479L, 6480L, 6481L, 6482L, 6483L, 6484L, 6485L, 6486L, 6487L, 6488L, 6489L, 6490L, 6491L, 6492L, 6493L, 6494L, 6495L, 6496L, 6497L, 6498L, 6499L, 6500L, 6501L, 6502L, 6503L, 6504L, 6505L, 6506L, 6507L, 6508L, 6509L, 6510L, 6511L, 6512L, 6513L, 6514L, 6515L, 6516L, 6517L, 6518L, 6519L, 6520L, 6521L, 6522L, 6523L, 6524L, 6525L, 6526L, 6527L, 6528L, 6529L, 6530L, 6531L, 6532L, 6533L, 6534L, 6536L, 6537L, 6538L, 6540L, 6541L, 6542L, 6543L, 6544L, 6545L, 6546L, 6547L, 6548L, 6549L, 6550L, 6551L, 6554L, 6555L, 6556L, 6557L, 6558L, 6559L, 6771L, 6772L, 6773L, 7161L, 7162L, 7163L, 7165L, 7166L, 7167L, 7168L, 7169L, 7170L, 7171L, 7172L, 7174L, 7175L, 7176L, 7177L, 7179L, 7180L, 7181L, 7182L, 7184L, 7185L, 7186L, 7187L, 7188L, 7189L, 7190L, 7191L, 7193L, 7194L, 7195L, 7196L, 7198L, 7199L, 7200L, 7201L, 8769L, 8770L, 8771L, 8772L, 8773L, 8774L, 8775L, 8776L, 8777L, 8778L, 8779L, 8780L, 8781L, 8782L, 8783L, 8784L, 8785L, 8786L, 8787L, 8788L, 8789L, 8790L, 8791L, 8792L, 8793L, 8794L, 8795L};
        String[] SKUcode = new String[]{"RBOKSSRM00920", "RBOKSSRM00919", "RBOKSSRM00918", "RBOKSSRM00921", "RBOKTPNM00999", "RBOKTPNM00998", "RBOKTPNM00997", "RBOKTPNM01000", "RBOKTPNM00983", "RBOKTPNM00982", "RBOKTPNM00981", "RBOKTPNM00984", "RBOKSSRM00915", "RBOKSSRM00914", "RBOKSSRM00913", "RBOKSSRM00916", "RBOKSSRM00917", "RBOKTPNM00995", "RBOKTPNM00994", "RBOKTPNM00993", "RBOKTPNM00996", "RBOKTPNM00987", "RBOKTPNM00986", "RBOKTPNM00985", "RBOKTPNM00988", "RBOKSSRM00924", "RBOKSSRM00912", "RBOKSSRM00923", "RBOKSSRM00925", "RBOKSSRM00926", "RBOKJKWU00225", "RBOKJKWU00224", "RBOKJKWU00223", "RBOKJKWU00226", "RBOKJKWU00221", "RBOKJKWU00220", "RBOKJKWU00219", "RBOKJKWU00222", "RBOKTSPM01166", "RBOKTSPM01165", "RBOKTSPM01164", "RBOKTSPM01167", "RBOKTSPM01162", "RBOKTSPM01161", "RBOKTSPM01160", "RBOKTSPM01163", "RBOKTSPM01158", "RBOKTSPM01157", "RBOKTSPM01156", "RBOKTSPM01159", "RBOKTSPM01154", "RBOKTSPM01153", "RBOKTSPM01152", "RBOKTSPM01155", "RBOKJYPU00528", "RBOKJYPU00527", "RBOKJYPU00581", "RBOKJYPU00529", "RBOKJYPU00579", "RBOKJYPU00578", "RBOKJYPU00580", "RBOKSHSM00830", "RBOKSHSM00829", "RBOKSTSM00955", "RBOKSTSM00954", "RBOKSTSM00953", "RBOKSTSM00956", "RBOKSTSM00959", "RBOKSTSM00958", "RBOKSTSM00957", "RBOKSTSM00960", "RBOKSTSM00951", "RBOKSTSM00952", "RBOKSTSM00961", "RBOKSTSM00962", "RBOKJKWU00215", "RBOKJKWU00214", "RBOKJKWU00216", "RBOKJKWU00217", "RBOKBKPK00105", "RBOKBKPK00106", "RBOKSHSM00833", "RBOKSHSM00834", "RBOKSHSM00835", "RBOKSHSM00870", "RBOKSHSM00836", "RBOKSHSM00837", "RBOKSHSM00838", "RBOKSHSM00839", "RBOKACMC00071", "RBOKSHSM00855", "RBOKSHSM00831", "RBOKSHSM00832", "RBOKSHSM00856", "RBOKSHSM00857", "RBOKSHSM00840", "RBOKSHSM00841", "RBOKSHSM00842", "RBOKSHSM00843", "RBOKSHSM00844", "RBOKSHSM00845", "RBOKSHSM00846", "RBOKSHSM00847", "RBOKSHSM00848", "RBOKSHSM00849", "RBOKSHSM00850", "RBOKSHSM00851", "RBOKSHSM00852", "RBOKSHSM00853", "RBOKSHSW00894", "RBOKSHSW00895", "RBOKSHSW00896", "RBOKSHSW00897", "RBOKSHSW00886", "RBOKSHSW00887", "RBOKSHSW00888", "RBOKSHSW00889", "RBOKSHSW00890", "RBOKSHSW00891", "RBOKSHSW00892", "RBOKSHSW00893", "RBOKJYMM00403", "RBOKJYMM00404", "RBOKJYMM00405", "RBOKJYMM00406", "RBOKJYMM00407", "RBOKTSRM01438", "RBOKTSRM01439", "RBOKTSRM01440", "RBOKTSRM01441", "RBOKTSPM01181", "RBOKSSRW00932", "RBOKSSRW00933", "RBOKSSRW00934", "RBOKTSPM01187", "RBOKTSPM01188", "RBOKTSPM01189", "RBOKTSPM01190", "RBOKTSRM01442", "RBOKTSRM01443", "RBOKTSRM01452", "RBOKTSRM01453", "RBOKTSRM01455", "RBOKTSRM01456", "RBOKTSRM01444", "RBOKTSPM01123", "RBOKTSPM01134", "RBOKTSPM01135", "RBOKTSPM01136", "RBOKTSPM01124", "RBOKTSPM01125", "RBOKTSPM01126", "RBOKTSPM01127", "RBOKTSPM01128", "RBOKTSPM01122", "RBOKTSPM01095", "RBOKTSPM01096", "RBOKTSPM01147", "RBOKSSRW00935", "RBOKTSRM01426", "RBOKTSRM01427", "RBOKTSRM01428", "RBOKTSRM01429", "RBOKTSPM01608", "RBOKTSPM01609", "RBOKTSPM01610", "RBOKTSPM01611", "RBOKTSPM01612", "RBOKJYMM00398", "RBOKJYMM00399", "RBOKJYMM00400", "RBOKJYMM00401", "RBOKJYMM00402", "RBOKTSRM01430", "RBOKTSRM01431", "RBOKTSRM01432", "RBOKTSRM01433", "RBOKTSRM01434", "RBOKTSVM01544", "RBOKTSVM01545", "RBOKTSVM01546", "RBOKTSVM01547", "RBOKTSVM01548", "RBOKTSVM01549", "RBOKTSVM01550", "RBOKTSVM01551", "RBOKTSVM01552", "RBOKTSVM01553", "RBOKTSPM01182", "RBOKTSPM01183", "RBOKTSPM01184", "RBOKTSPM01185", "RBOKTSPM01186", "RBOKTSRM01445", "RBOKTSRM01454", "RBOKTSPM01145", "RBOKTSPM01146", "RBOKTSPM01140", "RBOKTSPM01141", "RBOKTSPM01171", "RBOKTSPM01172", "RBOKTSPM01173", "RBOKTSPM01174", "RBOKTSPM01175", "RBOKTSPM01176", "RBOKTSPM01177", "RBOKTSPM01178", "RBOKTSPM01179", "RBOKTSPM01180", "RBOKTSRM01448", "RBOKTSRM01449", "RBOKTSRM01450", "RBOKTSRM01451", "RBOKTSPM01142", "RBOKTSPM04586", "RBOKTSPM01143", "RBOKTSPM01144", "RBOKTSPM01195", "RBOKTSPM01196", "RBOKTSPM01197", "RBOKTSPM01198", "RBOKTSPM01199", "RBOKTSPM01200", "RBOKTSRM01459", "RBOKTSRM01461", "RBOKTSRM01462", "RBOKSHSW00899", "RBOKSHSW00900", "RBOKSHSW00901", "RBOKSHSM00874", "RBOKSHSM00875", "RBOKTSRW01537", "RBOKTSRW01538", "RBOKTSRW01539", "RBOKTSRW01540", "RBOKTSRW01541", "RBOKTSRW01527", "RBOKTSRW01528", "RBOKTSRW01529", "RBOKTPMC00972", "RBOKTSRW00057", "RBOKTSRW01531", "RBOKTSRW01532", "RBOKTPMC00969", "RBOKTPMC00968", "RBOKTPMC00967", "RBOKTPMC00970", "RBOKTPMC00971", "RBOKCSLW00130", "RBOKCSLW00131", "RBOKCSLW00132", "RBOKCSLW00133", "RBOKCSLW00134", "RBOKCSLW00135", "RBOKTSRW01533", "RBOKTSRW01534", "RBOKTSRW01535", "RBOKTSRW01536", "RBOKSSRW00929", "RBOKSSRW00928", "RBOKSSRW00927", "RBOKSSRW00930", "RBOKSSRW00931", "RBOKJYPU00524", "RBOKJYPU00523", "RBOKJYPU00522", "RBOKJYPU00525", "RBOKJYPU00526", "RBOKTSRM01446", "RBOKTSRM01447", "RBOKTSRM01457", "RBOKTSRM01458", "RBOKTPNM00991", "RBOKTPNM00990", "RBOKTPNM00989", "RBOKTPNM00992", "RBOKTSRM04459", "RBOKTSRM04460", "RBOKTSRM04461", "RBOKTSRM04462", "RBOKTSRM04463", "RBOKTSRM04435", "RBOKTSRM04436", "RBOKTSRM04437", "RBOKTSRM04438", "RBOKTSRM04439", "RBOKTSRM04351", "RBOKTSRM04352", "RBOKTSRM04353", "RBOKTSRM04354", "RBOKTSRM04355", "RBOKTSPM04339", "RBOKTSPM04340", "RBOKTSPM04341", "RBOKTSPM04342", "RBOKTSPM04343", "RBOKTSRM04369", "RBOKTSRM04370", "RBOKTSRM04371", "RBOKTSRM04372", "RBOKTSRM04373", "RBOKTSPM04298", "RBOKTSPM04299", "RBOKTSPM04300", "RBOKTSPM04301", "RBOKTSRM04375", "RBOKTSRM04376", "RBOKTSRM04377", "RBOKTSRM04378", "RBOKTSRM04379", "RBOKTSRM04282", "RBOKTSRM04283", "RBOKTSRM04284", "RBOKTSRM04285", "RBOKTSRM04286", "RBOKTSRM04477", "RBOKTSRM04478", "RBOKTSRM04479", "RBOKTSRM04480", "RBOKTSRM04481", "RBOKTSRM04453", "RBOKTSRM04454", "RBOKTSRM04455", "RBOKTSRM04456", "RBOKTSRM04457", "RBOKTSRM04471", "RBOKTSRM04472", "RBOKTSRM04473", "RBOKTSRM04474", "RBOKTSRM04417", "RBOKTSRM04418", "RBOKTSRM04419", "RBOKTSRM04420", "RBOKTSRM04421", "RBOKTSRM04466", "RBOKTSRM04467", "RBOKTSRM04468", "RBOKTSRM04469", "RBOKTSRM04267", "RBOKTSRM04268", "RBOKTSRM04269", "RBOKTSRM04270", "RBOKTSRM04271", "RBOKTSRM04447", "RBOKTSRM04448", "RBOKTSRM04449", "RBOKTSRM04450", "RBOKTSRM04451", "RBOKTSPM04292", "RBOKTSPM04293", "RBOKTSPM04294", "RBOKTSPM04295", "RBOKTSPM04296", "RBOKTSRM04357", "RBOKTSRM04358", "RBOKTSRM04359", "RBOKTSRM04360", "RBOKTSRM04361", "RBOKTSRM04272", "RBOKTSRM04273", "RBOKTSRM04274", "RBOKTSRM04275", "RBOKTSRM04276", "RBOKTSPM04406", "RBOKTSPM04407", "RBOKTSPM04408", "RBOKTSPM04409", "RBOKTSRM04423", "RBOKTSRM04424", "RBOKTSRM04425", "RBOKTSRM04426", "RBOKTSRM04427", "RBOKTSRM04441", "RBOKTSRM04442", "RBOKTSRM04443", "RBOKTSRM04444", "RBOKTSRM04445", "RBOKTSRM04363", "RBOKTSRM04364", "RBOKTSRM04365", "RBOKTSRM04366", "RBOKTSPM04322", "RBOKTSPM04323", "RBOKTSPM04324", "RBOKTSPM04325", "RBOKTSPM04327", "RBOKTSPM04328", "RBOKTSPM04329", "RBOKTSPM04330", "RBOKTSPM04331", "RBOKTSPM04333", "RBOKTSPM04334", "RBOKTSPM04335", "RBOKTSPM04336", "RBOKTSPM04337", "RBOKTSRM04483", "RBOKTSRM04484", "RBOKTSRM04485", "RBOKTSRM04486", "RBOKTSRM04487", "RBOKTSPM04287", "RBOKTSPM04288", "RBOKTSPM04289", "RBOKTSPM04290", "RBOKTSPM04291", "RBOKTSPM04253", "RBOKTSPM04254", "RBOKTSPM04255", "RBOKTSPM04256", "RBOKTSRM04277", "RBOKTSRM04278", "RBOKTSRM04279", "RBOKTSRM04280", "RBOKTSPM01191", "RBOKTSPM04786", "RBOKTSPM04787", "RBOKTSPM04788", "RBOKSHSM04302", "RBOKSHSM04303", "RBOKSHSM04304", "RBOKSHSM04305", "RBOKSHSM04306", "RBOKSHSM04307", "RBOKSHSM04314", "RBOKSHSM04315", "RBOKSHSM04316", "RBOKSHSM04317", "RBOKSHSM04318", "RBOKSHSM04319", "RBOKSHSM04308", "RBOKSHSM04309", "RBOKSHSM04310", "RBOKSHSM04311", "RBOKSHSM04312", "RBOKSHSM04313", "RBOKTSPM01192", "RBOKTSPM01193", "RBOKTSPM01194", "RBOKTSRW04346", "RBOKTSRW04347", "RBOKTSRW04348", "RBOKTSRW04263", "RBOKTSRW04264", "RBOKTSRW04265", "RBOKTSRW04266", "RBOKTSRW04242", "RBOKTSRW04243", "RBOKTSRW04244", "RBOKTSRW04245", "RBOKTSRW04247", "RBOKTSRW04248", "RBOKTSRW04249", "RBOKTSRW04250", "RBOKTSRW04387", "RBOKTSRW04388", "RBOKTSRW04389", "RBOKTSRW04390", "RBOKTSRW04393", "RBOKTSRW04394", "RBOKTSRW04395", "RBOKTSRW04396", "RBOKTSRW04399", "RBOKTSRW04400", "RBOKTSRW04401", "RBOKTSRW04402", "RBOKTSRW04411", "RBOKTSRW04412", "RBOKTSRW04413", "RBOKTSRW04414", "RBOKTSRW04257", "RBOKTSRW04258", "RBOKTSRW04259", "RBOKTSRW04260", "RBOKJYFK00321", "RBOKJYFK00322", "RBOKJYFK00323", "RBOKJYFK00324", "RBOKJYFK00325", "RBOKJYFK00326", "RBOKJYFK00327", "RBOKJYFK00328", "RBOKJYFK00329", "RBOKJYFK00330", "RBOKJYFK00331", "RBOKJYFK00332", "RBOKJYFK00333", "RBOKJYFK00334", "RBOKJYFK00335", "RBOKJYFK00336", "RBOKJYFK00337", "RBOKJYFK00338", "RBOKJYFK00339", "RBOKJYFK00340", "RBOKJYFK00341", "RBOKJYFK00342", "RBOKJYFK00343", "RBOKJYFU00370", "RBOKJYFU00371", "RBOKJYFU00372", "RBOKJYFU00373"};

        public CreateOIPOPayload() {
        }

        public PurchaseOrderEntry purchaseOrder(boolean... multi) throws IOException {
            SKUTaxDetailEntry skuTaxDetailEntry1 = new SKUTaxDetailEntry();
            skuTaxDetailEntry1.setTaxCriteria(TaxCriteria.BUYING);
            skuTaxDetailEntry1.setTaxType(TaxType.CGST.toString());
            skuTaxDetailEntry1.setTaxPercent(Double.valueOf(2.0D));
            skuTaxDetailEntry1.setTaxAmount(Double.valueOf(6.15D));
            skuTaxDetailEntry1.setRemarks("Test SKU");
            SKUTaxDetailEntry skuTaxDetailEntry2 = new SKUTaxDetailEntry();
            skuTaxDetailEntry2.setTaxCriteria(TaxCriteria.BUYING);
            skuTaxDetailEntry2.setTaxType(TaxType.SGST.toString());
            skuTaxDetailEntry2.setTaxPercent(Double.valueOf(3.0D));
            skuTaxDetailEntry2.setTaxAmount(Double.valueOf(9.23D));
            skuTaxDetailEntry2.setRemarks("Test SKU");
            SKUTaxDetailEntry skuTaxDetailEntry3 = new SKUTaxDetailEntry();
            skuTaxDetailEntry3.setTaxCriteria(TaxCriteria.SELLING);
            skuTaxDetailEntry3.setTaxType(TaxType.CGST.toString());
            skuTaxDetailEntry3.setTaxPercent(Double.valueOf(2.0D));
            skuTaxDetailEntry3.setTaxAmount(Double.valueOf(13.31D));
            skuTaxDetailEntry3.setRemarks("Test SKU");
            SKUTaxDetailEntry skuTaxDetailEntry4 = new SKUTaxDetailEntry();
            skuTaxDetailEntry4.setTaxCriteria(TaxCriteria.SELLING);
            skuTaxDetailEntry4.setTaxType(TaxType.SGST.toString());
            skuTaxDetailEntry4.setTaxPercent(Double.valueOf(3.0D));
            skuTaxDetailEntry4.setTaxAmount(Double.valueOf(19.97D));
            skuTaxDetailEntry4.setRemarks("Test SKU");
            List<SKUTaxDetailEntry> skuTaxDetailEntries = new ArrayList();
            skuTaxDetailEntries.add(skuTaxDetailEntry1);
            skuTaxDetailEntries.add(skuTaxDetailEntry2);
            skuTaxDetailEntries.add(skuTaxDetailEntry3);
            skuTaxDetailEntries.add(skuTaxDetailEntry4);
            List<PurchaseOrderSKUEntry> purchaseOrderSKUEntries = new ArrayList();
            if(multi != null && multi.length != 0) {
                int number = this.skuIds.length;
                PurchaseOrderSKUEntry[] purchaseOrderSKUEntry = new PurchaseOrderSKUEntry[number];

                for(int i = 0; i < number; ++i) {
                    purchaseOrderSKUEntry[i] = new PurchaseOrderSKUEntry();
                    purchaseOrderSKUEntry[i].setSkuTaxDetailEntries(skuTaxDetailEntries);
                    purchaseOrderSKUEntry[i].setHsnCode("12345678");
                    purchaseOrderSKUEntry[i].setBrand("United Colors of Benetton");
                    purchaseOrderSKUEntry[i].setGtin("08903975170322");
                    purchaseOrderSKUEntry[i].setVendorArticleNumber("16A3H96E9001I");
                    purchaseOrderSKUEntry[i].setVendorArticleName("Basic Round- CORE solid");
                    purchaseOrderSKUEntry[i].setColour("W91");
                    purchaseOrderSKUEntry[i].setSize("L");
                    purchaseOrderSKUEntry[i].setListPrice(Double.valueOf(307.56D));
                    purchaseOrderSKUEntry[i].setQuantity(Integer.valueOf(38));
                    purchaseOrderSKUEntry[i].setMrp(Double.valueOf(699.0D));
                    purchaseOrderSKUEntry[i].setLandedPrice(Double.valueOf(322.94D));
                    purchaseOrderSKUEntry[i].setVendorTermsId(Long.valueOf(10833L));
                    purchaseOrderSKUEntry[i].setMarginPercent(Double.valueOf(56.0D));
                    purchaseOrderSKUEntry[i].setGrossMarginPercent(Double.valueOf(53.8D));
                    purchaseOrderSKUEntry[i].setMarginType("gross + taxes");
                    purchaseOrderSKUEntry[i].setSourceSkuId(Long.valueOf(2049566L));
                    purchaseOrderSKUEntry[i].setRemarks("Test SKU");
                    purchaseOrderSKUEntry[i].setCreditPeriod(Long.valueOf(10L));
                    purchaseOrderSKUEntry[i].setDiscount(Double.valueOf(10.0D));
                    purchaseOrderSKUEntry[i].setEstimatedDeliveryDate(new Date());
                    purchaseOrderSKUEntry[i].setExciseDuty(Double.valueOf(100.0D));
                    purchaseOrderSKUEntry[i].setExciseDutyPercent(Double.valueOf(10.0D));
                    purchaseOrderSKUEntry[i].setSkuId(Long.valueOf(this.skuIds[i]));
                    purchaseOrderSKUEntry[i].setSkuCode(this.SKUcode[i]);
                    purchaseOrderSKUEntries.add(purchaseOrderSKUEntry[i]);
                }
            } else {
                PurchaseOrderSKUEntry purchaseOrderSKUEntry = new PurchaseOrderSKUEntry();
                purchaseOrderSKUEntry.setSkuTaxDetailEntries(skuTaxDetailEntries);
                purchaseOrderSKUEntry.setHsnCode("12345678");
                purchaseOrderSKUEntry.setBrand("United Colors of Benetton");
                purchaseOrderSKUEntry.setGtin("08903975170322");
                purchaseOrderSKUEntry.setVendorArticleNumber("16A3H96E9001I");
                purchaseOrderSKUEntry.setVendorArticleName("Basic Round- CORE solid");
                purchaseOrderSKUEntry.setColour("W91");
                purchaseOrderSKUEntry.setSize("L");
                purchaseOrderSKUEntry.setListPrice(Double.valueOf(307.56D));
                purchaseOrderSKUEntry.setQuantity(Integer.valueOf(38));
                purchaseOrderSKUEntry.setMrp(Double.valueOf(699.0D));
                purchaseOrderSKUEntry.setLandedPrice(Double.valueOf(322.94D));
                purchaseOrderSKUEntry.setVendorTermsId(Long.valueOf(10833L));
                purchaseOrderSKUEntry.setMarginPercent(Double.valueOf(56.0D));
                purchaseOrderSKUEntry.setGrossMarginPercent(Double.valueOf(53.8D));
                purchaseOrderSKUEntry.setMarginType("gross + taxes");
                purchaseOrderSKUEntry.setSourceSkuId(Long.valueOf(2049566L));
                purchaseOrderSKUEntry.setCreditPeriod(Long.valueOf(10L));
                purchaseOrderSKUEntry.setDiscount(Double.valueOf(10.0D));
                purchaseOrderSKUEntry.setEstimatedDeliveryDate(new Date());
                purchaseOrderSKUEntry.setExciseDuty(Double.valueOf(100.0D));
                purchaseOrderSKUEntry.setExciseDutyPercent(Double.valueOf(10.0D));
                purchaseOrderSKUEntry.setSkuId(Long.valueOf(12070042L));
                purchaseOrderSKUEntry.setSkuCode("UCFBTSHT00122671");
                purchaseOrderSKUEntries.add(purchaseOrderSKUEntry);
            }

            Date date = new Date();
            PurchaseOrderEntry po = new PurchaseOrderEntry();
            po.setCreatedBy("dummyuserid");
            po.setMailTo("astha.sahay@madura.adityabirla.com,aditya.birla@myntra.com");
            po.setMailCc("nishima.kaler@myntra.com");
            po.setVendorId(Long.valueOf(8L));
            po.setVendorName("Benetton India Pvt. Ltd");
            po.setVendorContactPerson("Priya");
            po.setLetterHeading("Myntra Jabong India Pvt Ltd");
            po.setSeasonId(Long.valueOf(18L));
            po.setSeasonYear(Integer.valueOf(2017));
            po.setCategoryManagerEmail("Test.automation@myntra.com");
            po.setCategoryManagerName("Test Automation");
            po.setCommercialType("OUTRIGHT");
            po.setEstimatedShipmentDate(date);
            po.setWarehouseId(Long.valueOf(36L));
            po.setVendorAddressId(Long.valueOf(6324L));
            po.setCreditBasisAsOn("Date of GRN");
            po.setPaymentTerms("Cheque");
            po.setStockOrigin(StockOrigin.DOMESTIC);
            po.setBrandType(BrandType.EXTERNAL);
            po.setPrioritization(PurchaseOrderPrioritization.NORMAL);
            po.setWorkedBy("Automation Script");
            po.setSourceReferenceId("123");
            po.setBuyerId(Long.valueOf(3974L));
            po.setPurchaseOrderSKUEntries(purchaseOrderSKUEntries);
            po.setTaxRegime(TaxRegime.GST);
            po.setSplitPo(Boolean.valueOf(true));
            po.setComments("This is a automation PO");
            po.setDescription("This is a automation PO");
            po.setVendorContactNumber("9886108029");
            return po;
        }

        public PurchaseOrderEntry purchaseOrder(boolean multi, Integer qty) throws IOException {
            SKUTaxDetailEntry skuTaxDetailEntry1 = new SKUTaxDetailEntry();
            skuTaxDetailEntry1.setTaxCriteria(TaxCriteria.BUYING);
            skuTaxDetailEntry1.setTaxType(TaxType.CGST.toString());
            skuTaxDetailEntry1.setTaxPercent(Double.valueOf(2.0D));
            skuTaxDetailEntry1.setTaxAmount(Double.valueOf(6.15D));
            skuTaxDetailEntry1.setRemarks("Test SKU");
            SKUTaxDetailEntry skuTaxDetailEntry2 = new SKUTaxDetailEntry();
            skuTaxDetailEntry2.setTaxCriteria(TaxCriteria.BUYING);
            skuTaxDetailEntry2.setTaxType(TaxType.SGST.toString());
            skuTaxDetailEntry2.setTaxPercent(Double.valueOf(3.0D));
            skuTaxDetailEntry2.setTaxAmount(Double.valueOf(9.23D));
            skuTaxDetailEntry2.setRemarks("Test SKU");
            SKUTaxDetailEntry skuTaxDetailEntry3 = new SKUTaxDetailEntry();
            skuTaxDetailEntry3.setTaxCriteria(TaxCriteria.SELLING);
            skuTaxDetailEntry3.setTaxType(TaxType.CGST.toString());
            skuTaxDetailEntry3.setTaxPercent(Double.valueOf(2.0D));
            skuTaxDetailEntry3.setTaxAmount(Double.valueOf(13.31D));
            skuTaxDetailEntry3.setRemarks("Test SKU");
            SKUTaxDetailEntry skuTaxDetailEntry4 = new SKUTaxDetailEntry();
            skuTaxDetailEntry4.setTaxCriteria(TaxCriteria.SELLING);
            skuTaxDetailEntry4.setTaxType(TaxType.SGST.toString());
            skuTaxDetailEntry4.setTaxPercent(Double.valueOf(3.0D));
            skuTaxDetailEntry4.setTaxAmount(Double.valueOf(19.97D));
            skuTaxDetailEntry4.setRemarks("Test SKU");
            List<SKUTaxDetailEntry> skuTaxDetailEntries = new ArrayList();
            skuTaxDetailEntries.add(skuTaxDetailEntry1);
            skuTaxDetailEntries.add(skuTaxDetailEntry2);
            skuTaxDetailEntries.add(skuTaxDetailEntry3);
            skuTaxDetailEntries.add(skuTaxDetailEntry4);
            List<PurchaseOrderSKUEntry> purchaseOrderSKUEntries = new ArrayList();
            if(!multi) {
                PurchaseOrderSKUEntry purchaseOrderSKUEntry = new PurchaseOrderSKUEntry();
                purchaseOrderSKUEntry.setSkuTaxDetailEntries(skuTaxDetailEntries);
                purchaseOrderSKUEntry.setHsnCode("12345678");
                purchaseOrderSKUEntry.setBrand("United Colors of Benetton");
                purchaseOrderSKUEntry.setGtin("08903975170322");
                purchaseOrderSKUEntry.setVendorArticleNumber("16A3H96E9001I");
                purchaseOrderSKUEntry.setVendorArticleName("Basic Round- CORE solid");
                purchaseOrderSKUEntry.setColour("W91");
                purchaseOrderSKUEntry.setSize("L");
                purchaseOrderSKUEntry.setListPrice(Double.valueOf(307.56D));
                purchaseOrderSKUEntry.setQuantity(qty);
                purchaseOrderSKUEntry.setMrp(Double.valueOf(699.0D));
                purchaseOrderSKUEntry.setLandedPrice(Double.valueOf(322.94D));
                purchaseOrderSKUEntry.setVendorTermsId(Long.valueOf(10833L));
                purchaseOrderSKUEntry.setMarginPercent(Double.valueOf(56.0D));
                purchaseOrderSKUEntry.setGrossMarginPercent(Double.valueOf(53.8D));
                purchaseOrderSKUEntry.setMarginType("gross + taxes");
                purchaseOrderSKUEntry.setSourceSkuId(Long.valueOf(2049566L));
                purchaseOrderSKUEntry.setCreditPeriod(Long.valueOf(10L));
                purchaseOrderSKUEntry.setDiscount(Double.valueOf(10.0D));
                purchaseOrderSKUEntry.setEstimatedDeliveryDate(new Date());
                purchaseOrderSKUEntry.setExciseDuty(Double.valueOf(100.0D));
                purchaseOrderSKUEntry.setExciseDutyPercent(Double.valueOf(10.0D));
                purchaseOrderSKUEntry.setSkuId(Long.valueOf(12070042L));
                purchaseOrderSKUEntry.setSkuCode("UCFBTSHT00122671");
                purchaseOrderSKUEntries.add(purchaseOrderSKUEntry);
            } else {
                int number = this.skuIds.length;
                PurchaseOrderSKUEntry[] purchaseOrderSKUEntry = new PurchaseOrderSKUEntry[number];

                for(int i = 0; i < number; ++i) {
                    purchaseOrderSKUEntry[i] = new PurchaseOrderSKUEntry();
                    purchaseOrderSKUEntry[i].setSkuTaxDetailEntries(skuTaxDetailEntries);
                    purchaseOrderSKUEntry[i].setHsnCode("12345678");
                    purchaseOrderSKUEntry[i].setBrand("United Colors of Benetton");
                    purchaseOrderSKUEntry[i].setGtin("08903975170322");
                    purchaseOrderSKUEntry[i].setVendorArticleNumber("16A3H96E9001I");
                    purchaseOrderSKUEntry[i].setVendorArticleName("Basic Round- CORE solid");
                    purchaseOrderSKUEntry[i].setColour("W91");
                    purchaseOrderSKUEntry[i].setSize("L");
                    purchaseOrderSKUEntry[i].setListPrice(Double.valueOf(307.56D));
                    purchaseOrderSKUEntry[i].setQuantity(qty);
                    purchaseOrderSKUEntry[i].setMrp(Double.valueOf(699.0D));
                    purchaseOrderSKUEntry[i].setLandedPrice(Double.valueOf(322.94D));
                    purchaseOrderSKUEntry[i].setVendorTermsId(Long.valueOf(10833L));
                    purchaseOrderSKUEntry[i].setMarginPercent(Double.valueOf(56.0D));
                    purchaseOrderSKUEntry[i].setGrossMarginPercent(Double.valueOf(53.8D));
                    purchaseOrderSKUEntry[i].setMarginType("gross + taxes");
                    purchaseOrderSKUEntry[i].setSourceSkuId(Long.valueOf(2049566L));
                    purchaseOrderSKUEntry[i].setRemarks("Test SKU");
                    purchaseOrderSKUEntry[i].setCreditPeriod(Long.valueOf(10L));
                    purchaseOrderSKUEntry[i].setDiscount(Double.valueOf(10.0D));
                    purchaseOrderSKUEntry[i].setEstimatedDeliveryDate(new Date());
                    purchaseOrderSKUEntry[i].setExciseDuty(Double.valueOf(100.0D));
                    purchaseOrderSKUEntry[i].setExciseDutyPercent(Double.valueOf(10.0D));
                    purchaseOrderSKUEntry[i].setSkuId(Long.valueOf(this.skuIds[i]));
                    purchaseOrderSKUEntry[i].setSkuCode(this.SKUcode[i]);
                    purchaseOrderSKUEntries.add(purchaseOrderSKUEntry[i]);
                }
            }

            Date date = new Date();
            PurchaseOrderEntry po = new PurchaseOrderEntry();
            po.setCreatedBy("dummyuserid");
            po.setMailTo("astha.sahay@madura.adityabirla.com,aditya.birla@myntra.com");
            po.setMailCc("nishima.kaler@myntra.com");
            po.setVendorId(Long.valueOf(8L));
            po.setVendorName("Benetton India Pvt. Ltd");
            po.setVendorContactPerson("Priya");
            po.setLetterHeading("Myntra Jabong India Pvt Ltd");
            po.setSeasonId(Long.valueOf(18L));
            po.setSeasonYear(Integer.valueOf(2017));
            po.setCategoryManagerEmail("Test.automation@myntra.com");
            po.setCategoryManagerName("Test Automation");
            po.setCommercialType("OUTRIGHT");
            po.setEstimatedShipmentDate(date);
            po.setWarehouseId(Long.valueOf(36L));
            po.setVendorAddressId(Long.valueOf(6324L));
            po.setCreditBasisAsOn("Date of GRN");
            po.setPaymentTerms("Cheque");
            po.setStockOrigin(StockOrigin.DOMESTIC);
            po.setBrandType(BrandType.EXTERNAL);
            po.setPrioritization(PurchaseOrderPrioritization.NORMAL);
            po.setWorkedBy("Automation Script");
            po.setSourceReferenceId("123");
            po.setBuyerId(Long.valueOf(3974L));
            po.setPurchaseOrderSKUEntries(purchaseOrderSKUEntries);
            po.setTaxRegime(TaxRegime.GST);
            po.setSplitPo(Boolean.valueOf(true));
            po.setComments("This is a automation PO");
            po.setDescription("This is a automation PO");
            po.setVendorContactNumber("9886108029");
            return po;
        }

    public PurchaseOrderEntry purchaseOrder(boolean multi, Integer qty,Integer skuCount, Long buyerId) throws IOException {
        SKUTaxDetailEntry skuTaxDetailEntry1 = new SKUTaxDetailEntry();
        skuTaxDetailEntry1.setTaxCriteria(TaxCriteria.BUYING);
        skuTaxDetailEntry1.setTaxType(TaxType.CGST.toString());
        skuTaxDetailEntry1.setTaxPercent(Double.valueOf(2.0D));
        skuTaxDetailEntry1.setTaxAmount(Double.valueOf(6.15D));
        skuTaxDetailEntry1.setRemarks("Test SKU");
        SKUTaxDetailEntry skuTaxDetailEntry2 = new SKUTaxDetailEntry();
        skuTaxDetailEntry2.setTaxCriteria(TaxCriteria.BUYING);
        skuTaxDetailEntry2.setTaxType(TaxType.SGST.toString());
        skuTaxDetailEntry2.setTaxPercent(Double.valueOf(3.0D));
        skuTaxDetailEntry2.setTaxAmount(Double.valueOf(9.23D));
        skuTaxDetailEntry2.setRemarks("Test SKU");
        SKUTaxDetailEntry skuTaxDetailEntry3 = new SKUTaxDetailEntry();
        skuTaxDetailEntry3.setTaxCriteria(TaxCriteria.SELLING);
        skuTaxDetailEntry3.setTaxType(TaxType.CGST.toString());
        skuTaxDetailEntry3.setTaxPercent(Double.valueOf(2.0D));
        skuTaxDetailEntry3.setTaxAmount(Double.valueOf(13.31D));
        skuTaxDetailEntry3.setRemarks("Test SKU");
        SKUTaxDetailEntry skuTaxDetailEntry4 = new SKUTaxDetailEntry();
        skuTaxDetailEntry4.setTaxCriteria(TaxCriteria.SELLING);
        skuTaxDetailEntry4.setTaxType(TaxType.SGST.toString());
        skuTaxDetailEntry4.setTaxPercent(Double.valueOf(3.0D));
        skuTaxDetailEntry4.setTaxAmount(Double.valueOf(19.97D));
        skuTaxDetailEntry4.setRemarks("Test SKU");
        List<SKUTaxDetailEntry> skuTaxDetailEntries = new ArrayList();
        skuTaxDetailEntries.add(skuTaxDetailEntry1);
        skuTaxDetailEntries.add(skuTaxDetailEntry2);
        skuTaxDetailEntries.add(skuTaxDetailEntry3);
        skuTaxDetailEntries.add(skuTaxDetailEntry4);
        List<PurchaseOrderSKUEntry> purchaseOrderSKUEntries = new ArrayList();
        if(!multi) {
            PurchaseOrderSKUEntry purchaseOrderSKUEntry = new PurchaseOrderSKUEntry();
            purchaseOrderSKUEntry.setSkuTaxDetailEntries(skuTaxDetailEntries);
            purchaseOrderSKUEntry.setHsnCode("12345678");
            purchaseOrderSKUEntry.setBrand("United Colors of Benetton");
            purchaseOrderSKUEntry.setGtin("08903975170322");
            purchaseOrderSKUEntry.setVendorArticleNumber("16A3H96E9001I");
            purchaseOrderSKUEntry.setVendorArticleName("Basic Round- CORE solid");
            purchaseOrderSKUEntry.setColour("W91");
            purchaseOrderSKUEntry.setSize("L");
            purchaseOrderSKUEntry.setListPrice(Double.valueOf(307.56D));
            purchaseOrderSKUEntry.setQuantity(qty);
            purchaseOrderSKUEntry.setMrp(Double.valueOf(699.0D));
            purchaseOrderSKUEntry.setLandedPrice(Double.valueOf(322.94D));
            purchaseOrderSKUEntry.setVendorTermsId(Long.valueOf(10833L));
            purchaseOrderSKUEntry.setMarginPercent(Double.valueOf(56.0D));
            purchaseOrderSKUEntry.setGrossMarginPercent(Double.valueOf(53.8D));
            purchaseOrderSKUEntry.setMarginType("gross + taxes");
            purchaseOrderSKUEntry.setSourceSkuId(Long.valueOf(2049566L));
            purchaseOrderSKUEntry.setCreditPeriod(Long.valueOf(10L));
            purchaseOrderSKUEntry.setDiscount(Double.valueOf(10.0D));
            purchaseOrderSKUEntry.setEstimatedDeliveryDate(new Date());
            purchaseOrderSKUEntry.setExciseDuty(Double.valueOf(100.0D));
            purchaseOrderSKUEntry.setExciseDutyPercent(Double.valueOf(10.0D));
            purchaseOrderSKUEntry.setSkuId(Long.valueOf(12070042L));
            purchaseOrderSKUEntry.setSkuCode("UCFBTSHT00122671");
            purchaseOrderSKUEntries.add(purchaseOrderSKUEntry);
        } else {
            int number = skuCount;
            PurchaseOrderSKUEntry[] purchaseOrderSKUEntry = new PurchaseOrderSKUEntry[number];

            for(int i = 0; i < number; ++i) {
                purchaseOrderSKUEntry[i] = new PurchaseOrderSKUEntry();
                purchaseOrderSKUEntry[i].setSkuTaxDetailEntries(skuTaxDetailEntries);
                purchaseOrderSKUEntry[i].setHsnCode("12345678");
                purchaseOrderSKUEntry[i].setBrand("United Colors of Benetton");
                purchaseOrderSKUEntry[i].setGtin("08903975170322");
                purchaseOrderSKUEntry[i].setVendorArticleNumber("16A3H96E9001I");
                purchaseOrderSKUEntry[i].setVendorArticleName("Basic Round- CORE solid");
                purchaseOrderSKUEntry[i].setColour("W91");
                purchaseOrderSKUEntry[i].setSize("L");
                purchaseOrderSKUEntry[i].setListPrice(Double.valueOf(307.56D));
                purchaseOrderSKUEntry[i].setQuantity(qty);
                purchaseOrderSKUEntry[i].setMrp(Double.valueOf(699.0D));
                purchaseOrderSKUEntry[i].setLandedPrice(Double.valueOf(322.94D));
                purchaseOrderSKUEntry[i].setVendorTermsId(Long.valueOf(10833L));
                purchaseOrderSKUEntry[i].setMarginPercent(Double.valueOf(56.0D));
                purchaseOrderSKUEntry[i].setGrossMarginPercent(Double.valueOf(53.8D));
                purchaseOrderSKUEntry[i].setMarginType("gross + taxes");
                purchaseOrderSKUEntry[i].setSourceSkuId(Long.valueOf(2049566L));
                purchaseOrderSKUEntry[i].setRemarks("Test SKU");
                purchaseOrderSKUEntry[i].setCreditPeriod(Long.valueOf(10L));
                purchaseOrderSKUEntry[i].setDiscount(Double.valueOf(10.0D));
                purchaseOrderSKUEntry[i].setEstimatedDeliveryDate(new Date());
                purchaseOrderSKUEntry[i].setExciseDuty(Double.valueOf(100.0D));
                purchaseOrderSKUEntry[i].setExciseDutyPercent(Double.valueOf(10.0D));
                purchaseOrderSKUEntry[i].setSkuId(Long.valueOf(this.skuIds[i]));
                purchaseOrderSKUEntry[i].setSkuCode(this.SKUcode[i]);
                purchaseOrderSKUEntries.add(purchaseOrderSKUEntry[i]);
            }
        }

        Date date = new Date();
        PurchaseOrderEntry po = new PurchaseOrderEntry();
        po.setCreatedBy("dummyuserid");
        po.setMailTo("astha.sahay@madura.adityabirla.com,aditya.birla@myntra.com");
        po.setMailCc("nishima.kaler@myntra.com");
        po.setVendorId(Long.valueOf(8L));
        po.setVendorName("Benetton India Pvt. Ltd");
        po.setVendorContactPerson("Priya");
        po.setLetterHeading("Myntra Jabong India Pvt Ltd");
        po.setSeasonId(Long.valueOf(18L));
        po.setSeasonYear(Integer.valueOf(2017));
        po.setCategoryManagerEmail("Test.automation@myntra.com");
        po.setCategoryManagerName("Test Automation");
        po.setCommercialType("OUTRIGHT");
        po.setEstimatedShipmentDate(date);
        po.setWarehouseId(Long.valueOf(36L));
        po.setVendorAddressId(Long.valueOf(6324L));
        po.setCreditBasisAsOn("Date of GRN");
        po.setPaymentTerms("Cheque");
        po.setStockOrigin(StockOrigin.DOMESTIC);
        po.setBrandType(BrandType.EXTERNAL);
        po.setPrioritization(PurchaseOrderPrioritization.NORMAL);
        po.setWorkedBy("Automation Script");
        po.setSourceReferenceId("123");
        po.setBuyerId(buyerId);
        po.setPurchaseOrderSKUEntries(purchaseOrderSKUEntries);
        po.setTaxRegime(TaxRegime.GST);
        po.setSplitPo(Boolean.valueOf(true));
        po.setComments("This is a automation PO");
        po.setDescription("This is a automation PO");
        po.setVendorContactNumber("9886108029");
        return po;
    }

        public PurchaseOrderEntry createPayload(boolean... multi) throws IOException {
            PurchaseOrderEntry purchaseOrderEntry = this.purchaseOrder(multi);
            return purchaseOrderEntry;
        }

        public PurchaseOrderEntry createPayload(boolean multi, Integer qty) throws IOException {
            PurchaseOrderEntry purchaseOrderEntry = this.purchaseOrder(multi, qty);
            return purchaseOrderEntry;
        }

    public PurchaseOrderEntry createPayload(boolean multi, Integer qty,Integer skuCount,Long buyerId) throws IOException {
        PurchaseOrderEntry purchaseOrderEntry = this.purchaseOrder(multi, qty, skuCount,buyerId);
        return purchaseOrderEntry;
    }

}
