/*
 * Copyright (c) 2019. Edward Curren
 */

package com.pluralsight.duckair.app;

import com.pluralsight.duckair.model.FlightPlans;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheEntry;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.stream.StreamVisitor;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class StreamVisitorApp {

    public static void main(String[] args) {
        Ignition.setClientMode(true);
        try (Ignite ignite = Ignition.start("DuckAirlines-server.xml")) {
            ignite.destroyCache("FlightPlanStreamCache");

            createFlightStreamCache();

            IgniteCache<String, FlightPlans> flightPlansStreamCache = ignite.getOrCreateCache("FlightPlanStreamCache");
            flightPlansStreamCache.clear();
            loadData(flightPlansStreamCache);

            try (IgniteDataStreamer<String, FlightPlans> streamer =
                         ignite.dataStreamer("FlightPlanStreamCache")) {
                streamer.receiver(StreamVisitor.from((cache, e) -> {
                    FlightPlans value = e.getValue();
                    value.setFlightStatus("Cancelled");
                    e.setValue(value);
                    System.out.println(e.toString());
                }));

                getCancelledFlightList(flightPlansStreamCache, ignite, streamer);
            }
        }
    }

    private static void getCancelledFlightList(IgniteCache<String, FlightPlans> cache, Ignite ignite, IgniteDataStreamer<String, FlightPlans> stmr) {

        Set<String> keyList = new HashSet<>(Arrays.asList("04D491A4-F090-4504-B8F6-E1ED91B9CD3B", "977999EA-6C35-4A7F-B4C5-6CB6831C52C0", "71BCF869-CCD5-489B-A22E-C58BC29831DC", "C7544756-F8AA-4958-8598-8285945E9273",
                "A2039D19-4800-46A2-92D6-7B491D433656", "8B353B63-AFA8-4D07-B164-26DA1509F3E2", "21F1DFDE-DA68-4EF6-A9EA-EF349F346F38", "5333EC9E-DD21-49C0-8D79-90D30650A288",
                "38CC51CA-8917-4B6E-908D-A8893ED82C12", "43D82A51-7C7B-4B63-951A-9CFFF41DC322"));

        // Allow data updates.
        stmr.allowOverwrite(true);
        Collection<CacheEntry<String, FlightPlans>> entries = cache.getEntries(keyList);
        for(CacheEntry<String, FlightPlans> entry : entries) {
            stmr.addData(entry.getKey(), entry.getValue());
        }
    }

    private static CacheConfiguration<String, FlightPlans> createFlightStreamCache() {
        CacheConfiguration<String, FlightPlans> ccfg = new CacheConfiguration<>("FlightPlanStreamCache1");
        ccfg.setCacheMode(CacheMode.PARTITIONED);
        ccfg.setAtomicityMode(CacheAtomicityMode.ATOMIC);
        ccfg.setIndexedTypes(String.class, FlightPlans.class);

        return ccfg;
    }

    private static void loadData(IgniteCache<String, FlightPlans> cache) {

        FlightPlans flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA354");
        flightPlan.setFromAirportIcao("KPDX");
        flightPlan.setToAirportIcao("KATL");
        flightPlan.setDistance("1919");
        flightPlan.setFiledAltitude("30000");
        flightPlan.setFiledAirspeed("280");
        flightPlan.setFleetId("e8e9816d-57ee-43a8-8d0c-45c07fd5713e");
        flightPlan.setEstimatedMinutesEnroute(411);
        flightPlan.setFlightStatus("Ready");
        cache.put("8155DEEC-10C0-49A2-969E-CB5C66122966", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA137");
        flightPlan.setFromAirportIcao("KDEN");
        flightPlan.setToAirportIcao("KMIA");
        flightPlan.setDistance("1535");
        flightPlan.setFiledAltitude("25000");
        flightPlan.setFiledAirspeed("210");
        flightPlan.setFleetId("d66ba95c-d55a-46f9-9068-2a333b69aa74");
        flightPlan.setEstimatedMinutesEnroute(439);
        flightPlan.setFlightStatus("Ready");
        cache.put("C3A3FEAD-9AC2-4D62-BB28-D237A9F49E04", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA982");
        flightPlan.setFromAirportIcao("KPDX");
        flightPlan.setToAirportIcao("KBWI");
        flightPlan.setDistance("2056");
        flightPlan.setFiledAltitude("30000");
        flightPlan.setFiledAirspeed("210");
        flightPlan.setFleetId("7556e4a3-381c-4df8-8dce-99f8b8725418");
        flightPlan.setEstimatedMinutesEnroute(587);
        flightPlan.setFlightStatus("Ready");
        cache.put("7BF84823-1326-4AA1-A9F3-602FDBFFA138", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA149");
        flightPlan.setFromAirportIcao("KATL");
        flightPlan.setToAirportIcao("KMDW");
        flightPlan.setDistance("517");
        flightPlan.setFiledAltitude("30000");
        flightPlan.setFiledAirspeed("210");
        flightPlan.setFleetId("ad21d75c-0ad1-4fdb-b7e2-b1e0460eb775");
        flightPlan.setEstimatedMinutesEnroute(148);
        flightPlan.setFlightStatus("Ready");
        cache.put("089051B3-5482-4FA1-AD25-EB3AD9318DCD", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA366");
        flightPlan.setFromAirportIcao("KDEN");
        flightPlan.setToAirportIcao("KLAS");
        flightPlan.setDistance("549");
        flightPlan.setFiledAltitude("25000");
        flightPlan.setFiledAirspeed("250");
        flightPlan.setFleetId("2871374c-1e32-495f-a5eb-86bdae04476d");
        flightPlan.setEstimatedMinutesEnroute(132);
        flightPlan.setFlightStatus("Ready");
        cache.put("97B9A7B3-F576-4763-B3D2-41A7910F411F", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA256");
        flightPlan.setFromAirportIcao("KLGA");
        flightPlan.setToAirportIcao("KBOS");
        flightPlan.setDistance("160");
        flightPlan.setFiledAltitude("35000");
        flightPlan.setFiledAirspeed("210");
        flightPlan.setFleetId("d66ba95c-d55a-46f9-9068-2a333b69aa74");
        flightPlan.setEstimatedMinutesEnroute(46);
        flightPlan.setFlightStatus("Ready");
        cache.put("43D82A51-7C7B-4B63-951A-9CFFF41DC322", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA915");
        flightPlan.setFromAirportIcao("KJFK");
        flightPlan.setToAirportIcao("KIAH");
        flightPlan.setDistance("1248");
        flightPlan.setFiledAltitude("35000");
        flightPlan.setFiledAirspeed("210");
        flightPlan.setFleetId("9e78a04e-9830-4450-8941-941f12553f88");
        flightPlan.setEstimatedMinutesEnroute(357);
        flightPlan.setFlightStatus("Ready");
        cache.put("1B105985-62E8-434F-A9CE-E8E07C084ADB", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA539");
        flightPlan.setFromAirportIcao("KMDW");
        flightPlan.setToAirportIcao("KJFK");
        flightPlan.setDistance("635");
        flightPlan.setFiledAltitude("30000");
        flightPlan.setFiledAirspeed("210");
        flightPlan.setFleetId("d66ba95c-d55a-46f9-9068-2a333b69aa74");
        flightPlan.setEstimatedMinutesEnroute(181);
        flightPlan.setFlightStatus("Ready");
        cache.put("1AA26349-109A-42A1-925E-8B5D26542D6D", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA502");
        flightPlan.setFromAirportIcao("KMDW");
        flightPlan.setToAirportIcao("KMIA");
        flightPlan.setDistance("1035");
        flightPlan.setFiledAltitude("30000");
        flightPlan.setFiledAirspeed("250");
        flightPlan.setFleetId("0c757e27-5c70-4dca-8298-b17393f07bb3");
        flightPlan.setEstimatedMinutesEnroute(248);
        flightPlan.setFlightStatus("Ready");
        cache.put("04D491A4-F090-4504-B8F6-E1ED91B9CD3B", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA557");
        flightPlan.setFromAirportIcao("KCLT");
        flightPlan.setToAirportIcao("KORD");
        flightPlan.setDistance("528");
        flightPlan.setFiledAltitude("35000");
        flightPlan.setFiledAirspeed("280");
        flightPlan.setFleetId("7e33ced7-a897-4a8b-afdd-c7d609636246");
        flightPlan.setEstimatedMinutesEnroute(113);
        flightPlan.setFlightStatus("Ready");
        cache.put("A4511893-247B-488C-B89C-1E51D7705108", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA800");
        flightPlan.setFromAirportIcao("KMDW");
        flightPlan.setToAirportIcao("KLAS");
        flightPlan.setDistance("1323");
        flightPlan.setFiledAltitude("25000");
        flightPlan.setFiledAirspeed("210");
        flightPlan.setFleetId("e8e9816d-57ee-43a8-8d0c-45c07fd5713e");
        flightPlan.setEstimatedMinutesEnroute(378);
        flightPlan.setFlightStatus("Ready");
        cache.put("62F8483C-7DC7-4BA5-947A-C4A1C0C1D372", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA629");
        flightPlan.setFromAirportIcao("KBOS");
        flightPlan.setToAirportIcao("KBWI");
        flightPlan.setDistance("322");
        flightPlan.setFiledAltitude("30000");
        flightPlan.setFiledAirspeed("280");
        flightPlan.setFleetId("0393c1e9-58a4-47f7-a15f-e1f1aa73b732");
        flightPlan.setEstimatedMinutesEnroute(69);
        flightPlan.setFlightStatus("Ready");
        cache.put("F41AAD9C-9E0A-48B7-A116-D9B916AF0F49", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA287");
        flightPlan.setFromAirportIcao("KLAS");
        flightPlan.setToAirportIcao("KMIA");
        flightPlan.setDistance("1921");
        flightPlan.setFiledAltitude("25000");
        flightPlan.setFiledAirspeed("280");
        flightPlan.setFleetId("9304e3ff-9694-43c8-bdb6-acdd565ec46b");
        flightPlan.setEstimatedMinutesEnroute(412);
        flightPlan.setFlightStatus("Ready");
        cache.put("48E81C74-8B02-4268-8C0A-C3AC44B25BB2", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA719");
        flightPlan.setFromAirportIcao("KMSP");
        flightPlan.setToAirportIcao("KATL");
        flightPlan.setDistance("803");
        flightPlan.setFiledAltitude("25000");
        flightPlan.setFiledAirspeed("250");
        flightPlan.setFleetId("6f9a00e8-2b9f-4b60-8e10-6501fcb39062");
        flightPlan.setEstimatedMinutesEnroute(193);
        flightPlan.setFlightStatus("Ready");
        cache.put("F8B682A4-E452-4995-A395-6C8B461FC01F", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA829");
        flightPlan.setFromAirportIcao("KBOS");
        flightPlan.setToAirportIcao("KMCO");
        flightPlan.setDistance("998");
        flightPlan.setFiledAltitude("20000");
        flightPlan.setFiledAirspeed("250");
        flightPlan.setFleetId("2871374c-1e32-495f-a5eb-86bdae04476d");
        flightPlan.setEstimatedMinutesEnroute(240);
        flightPlan.setFlightStatus("Ready");
        cache.put("3566A0AB-302C-43B6-BD90-32FEC83A6C58", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA620");
        flightPlan.setFromAirportIcao("KMSP");
        flightPlan.setToAirportIcao("KFLL");
        flightPlan.setDistance("1314");
        flightPlan.setFiledAltitude("35000");
        flightPlan.setFiledAirspeed("250");
        flightPlan.setFleetId("806dbd82-f4c7-464c-8499-4e5363c71b15");
        flightPlan.setEstimatedMinutesEnroute(315);
        flightPlan.setFlightStatus("Ready");
        cache.put("AC77E518-C550-4189-991F-50286E422406", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA962");
        flightPlan.setFromAirportIcao("KLAS");
        flightPlan.setToAirportIcao("KBOS");
        flightPlan.setDistance("2070");
        flightPlan.setFiledAltitude("25000");
        flightPlan.setFiledAirspeed("210");
        flightPlan.setFleetId("2871374c-1e32-495f-a5eb-86bdae04476d");
        flightPlan.setEstimatedMinutesEnroute(591);
        flightPlan.setFlightStatus("Ready");
        cache.put("733E2398-8E22-4605-B09E-B81712BE2F94", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA091");
        flightPlan.setFromAirportIcao("KEWR");
        flightPlan.setToAirportIcao("KDCA");
        flightPlan.setDistance("173");
        flightPlan.setFiledAltitude("35000");
        flightPlan.setFiledAirspeed("280");
        flightPlan.setFleetId("d66ba95c-d55a-46f9-9068-2a333b69aa74");
        flightPlan.setEstimatedMinutesEnroute(37);
        flightPlan.setFlightStatus("Ready");
        cache.put("68EA1168-BCC7-4180-A425-85013BAC32D6", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA172");
        flightPlan.setFromAirportIcao("KDTW");
        flightPlan.setToAirportIcao("KEWR");
        flightPlan.setDistance("424");
        flightPlan.setFiledAltitude("20000");
        flightPlan.setFiledAirspeed("250");
        flightPlan.setFleetId("806dbd82-f4c7-464c-8499-4e5363c71b15");
        flightPlan.setEstimatedMinutesEnroute(102);
        flightPlan.setFlightStatus("Ready");
        cache.put("804800F6-0D70-4F87-9543-681853BA4E64", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA284");
        flightPlan.setFromAirportIcao("KLAS");
        flightPlan.setToAirportIcao("KTPA");
        flightPlan.setDistance("1773");
        flightPlan.setFiledAltitude("30000");
        flightPlan.setFiledAirspeed("250");
        flightPlan.setFleetId("4a3531b8-af11-4fc4-8300-023317013a07");
        flightPlan.setEstimatedMinutesEnroute(426);
        flightPlan.setFlightStatus("Ready");
        cache.put("38CC51CA-8917-4B6E-908D-A8893ED82C12", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA470");
        flightPlan.setFromAirportIcao("KLGA");
        flightPlan.setToAirportIcao("KJFK");
        flightPlan.setDistance("10");
        flightPlan.setFiledAltitude("25000");
        flightPlan.setFiledAirspeed("280");
        flightPlan.setFleetId("d2a153a9-0054-4690-8c72-dcb709ce85f0");
        flightPlan.setEstimatedMinutesEnroute(2);
        flightPlan.setFlightStatus("Ready");
        cache.put("3CB2A5FD-C99B-419A-946E-88BA85167C83", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA892");
        flightPlan.setFromAirportIcao("KPHL");
        flightPlan.setToAirportIcao("KDFW");
        flightPlan.setDistance("1132");
        flightPlan.setFiledAltitude("25000");
        flightPlan.setFiledAirspeed("250");
        flightPlan.setFleetId("ba96b780-4f5e-403e-99f9-e9b547272b21");
        flightPlan.setEstimatedMinutesEnroute(272);
        flightPlan.setFlightStatus("Ready");
        cache.put("6EF4B2D2-04A1-43FC-A8ED-9F1EEA267016", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA610");
        flightPlan.setFromAirportIcao("KBWI");
        flightPlan.setToAirportIcao("KEWR");
        flightPlan.setDistance("147");
        flightPlan.setFiledAltitude("30000");
        flightPlan.setFiledAirspeed("250");
        flightPlan.setFleetId("0875ba1c-baa4-4edd-abe8-744ed473826b");
        flightPlan.setEstimatedMinutesEnroute(35);
        flightPlan.setFlightStatus("Ready");
        cache.put("CC60BA65-9FBE-499A-88BC-DA3FD11C179B", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA685");
        flightPlan.setFromAirportIcao("KMCO");
        flightPlan.setToAirportIcao("KLAS");
        flightPlan.setDistance("1802");
        flightPlan.setFiledAltitude("20000");
        flightPlan.setFiledAirspeed("210");
        flightPlan.setFleetId("a134fd89-b6e7-4fe7-a3e3-87edf56bb962");
        flightPlan.setEstimatedMinutesEnroute(515);
        flightPlan.setFlightStatus("Ready");
        cache.put("47AAABE8-9432-4D48-B3BB-3BDC6043BF7F", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA115");
        flightPlan.setFromAirportIcao("KIAD");
        flightPlan.setToAirportIcao("KJFK");
        flightPlan.setDistance("198");
        flightPlan.setFiledAltitude("25000");
        flightPlan.setFiledAirspeed("280");
        flightPlan.setFleetId("806dbd82-f4c7-464c-8499-4e5363c71b15");
        flightPlan.setEstimatedMinutesEnroute(42);
        flightPlan.setFlightStatus("Ready");
        cache.put("4A078778-3644-4E6C-8212-51A9B6163781", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA268");
        flightPlan.setFromAirportIcao("KFLL");
        flightPlan.setToAirportIcao("KMIA");
        flightPlan.setDistance("19");
        flightPlan.setFiledAltitude("25000");
        flightPlan.setFiledAirspeed("250");
        flightPlan.setFleetId("e8e9816d-57ee-43a8-8d0c-45c07fd5713e");
        flightPlan.setEstimatedMinutesEnroute(5);
        flightPlan.setFlightStatus("Ready");
        cache.put("900B283D-F75E-4179-9F16-CC1D87A17BD0", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA742");
        flightPlan.setFromAirportIcao("KATL");
        flightPlan.setToAirportIcao("KSFO");
        flightPlan.setDistance("1873");
        flightPlan.setFiledAltitude("20000");
        flightPlan.setFiledAirspeed("250");
        flightPlan.setFleetId("6f9a00e8-2b9f-4b60-8e10-6501fcb39062");
        flightPlan.setEstimatedMinutesEnroute(450);
        flightPlan.setFlightStatus("Ready");
        cache.put("CFB375CC-CFA7-4558-A23F-B89EF2C81CFC", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA905");
        flightPlan.setFromAirportIcao("KDCA");
        flightPlan.setToAirportIcao("KPHX");
        flightPlan.setDistance("1722");
        flightPlan.setFiledAltitude("30000");
        flightPlan.setFiledAirspeed("210");
        flightPlan.setFleetId("d2a153a9-0054-4690-8c72-dcb709ce85f0");
        flightPlan.setEstimatedMinutesEnroute(492);
        flightPlan.setFlightStatus("Ready");
        cache.put("C9D59E00-10BC-4CEA-8B8B-DFC992A58CB9", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA775");
        flightPlan.setFromAirportIcao("KPHL");
        flightPlan.setToAirportIcao("KMSP");
        flightPlan.setDistance("867");
        flightPlan.setFiledAltitude("25000");
        flightPlan.setFiledAirspeed("210");
        flightPlan.setFleetId("d2a153a9-0054-4690-8c72-dcb709ce85f0");
        flightPlan.setEstimatedMinutesEnroute(248);
        flightPlan.setFlightStatus("Ready");
        cache.put("5A14A232-6517-4BEA-A4A1-F39FE5727C94", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA158");
        flightPlan.setFromAirportIcao("KLAS");
        flightPlan.setToAirportIcao("KDFW");
        flightPlan.setDistance("923");
        flightPlan.setFiledAltitude("20000");
        flightPlan.setFiledAirspeed("280");
        flightPlan.setFleetId("4863d02a-b8f2-4f0d-9b7a-dbd32ba2dfb3");
        flightPlan.setEstimatedMinutesEnroute(198);
        flightPlan.setFlightStatus("Ready");
        cache.put("A452DBEF-1477-4CAD-84F1-53B5BD942AE3", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA875");
        flightPlan.setFromAirportIcao("KSAN");
        flightPlan.setToAirportIcao("KMDW");
        flightPlan.setDistance("1510");
        flightPlan.setFiledAltitude("30000");
        flightPlan.setFiledAirspeed("210");
        flightPlan.setFleetId("b1ebe9ca-5d82-41c3-81e6-7fb9c97cbba2");
        flightPlan.setEstimatedMinutesEnroute(431);
        flightPlan.setFlightStatus("Ready");
        cache.put("71BCF869-CCD5-489B-A22E-C58BC29831DC", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA483");
        flightPlan.setFromAirportIcao("KSFO");
        flightPlan.setToAirportIcao("KTPA");
        flightPlan.setDistance("2141");
        flightPlan.setFiledAltitude("35000");
        flightPlan.setFiledAirspeed("280");
        flightPlan.setFleetId("9f4d6400-2e2a-47bc-b6f4-76dae10c9ecc");
        flightPlan.setEstimatedMinutesEnroute(459);
        flightPlan.setFlightStatus("Ready");
        cache.put("C9051FC5-2DE4-441B-B7B5-9396BB58F2E2", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA057");
        flightPlan.setFromAirportIcao("KSAN");
        flightPlan.setToAirportIcao("KMDW");
        flightPlan.setDistance("1510");
        flightPlan.setFiledAltitude("20000");
        flightPlan.setFiledAirspeed("280");
        flightPlan.setFleetId("2871374c-1e32-495f-a5eb-86bdae04476d");
        flightPlan.setEstimatedMinutesEnroute(324);
        flightPlan.setFlightStatus("Ready");
        cache.put("56A7BCF6-09E0-4852-B9C5-06605C891B41", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA327");
        flightPlan.setFromAirportIcao("KATL");
        flightPlan.setToAirportIcao("KDTW");
        flightPlan.setDistance("524");
        flightPlan.setFiledAltitude("35000");
        flightPlan.setFiledAirspeed("250");
        flightPlan.setFleetId("9304e3ff-9694-43c8-bdb6-acdd565ec46b");
        flightPlan.setEstimatedMinutesEnroute(126);
        flightPlan.setFlightStatus("Ready");
        cache.put("21F1DFDE-DA68-4EF6-A9EA-EF349F346F38", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA174");
        flightPlan.setFromAirportIcao("KPDX");
        flightPlan.setToAirportIcao("KTPA");
        flightPlan.setDistance("2226");
        flightPlan.setFiledAltitude("35000");
        flightPlan.setFiledAirspeed("280");
        flightPlan.setFleetId("b63b4c89-6966-43f1-9ea6-7dc8b4d2ed6b");
        flightPlan.setEstimatedMinutesEnroute(477);
        flightPlan.setFlightStatus("Ready");
        cache.put("4B804141-A49B-49BA-9BC2-917F0C9FC2E2", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA158");
        flightPlan.setFromAirportIcao("KCLT");
        flightPlan.setToAirportIcao("KORD");
        flightPlan.setDistance("528");
        flightPlan.setFiledAltitude("35000");
        flightPlan.setFiledAirspeed("280");
        flightPlan.setFleetId("4a3531b8-af11-4fc4-8300-023317013a07");
        flightPlan.setEstimatedMinutesEnroute(113);
        flightPlan.setFlightStatus("Ready");
        cache.put("94FD5BF1-6544-4D5F-961C-F9EE2888FB65", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA593");
        flightPlan.setFromAirportIcao("KIAD");
        flightPlan.setToAirportIcao("KLAX");
        flightPlan.setDistance("1990");
        flightPlan.setFiledAltitude("20000");
        flightPlan.setFiledAirspeed("280");
        flightPlan.setFleetId("d2a153a9-0054-4690-8c72-dcb709ce85f0");
        flightPlan.setEstimatedMinutesEnroute(426);
        flightPlan.setFlightStatus("Ready");
        cache.put("6A946766-86A7-435A-8D2F-4AC9FC5E0B02", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA371");
        flightPlan.setFromAirportIcao("KTPA");
        flightPlan.setToAirportIcao("KJFK");
        flightPlan.setDistance("881");
        flightPlan.setFiledAltitude("20000");
        flightPlan.setFiledAirspeed("280");
        flightPlan.setFleetId("d2a153a9-0054-4690-8c72-dcb709ce85f0");
        flightPlan.setEstimatedMinutesEnroute(189);
        flightPlan.setFlightStatus("Ready");
        cache.put("C7A97E01-F507-4171-9696-E43B311DF036", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA855");
        flightPlan.setFromAirportIcao("KTPA");
        flightPlan.setToAirportIcao("KPHL");
        flightPlan.setDistance("807");
        flightPlan.setFiledAltitude("20000");
        flightPlan.setFiledAirspeed("250");
        flightPlan.setFleetId("95794afd-d1ff-4b08-b287-3c59a4799a44");
        flightPlan.setEstimatedMinutesEnroute(194);
        flightPlan.setFlightStatus("Ready");
        cache.put("CD134CC5-97AA-44C0-8251-DDA99870972E", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA285");
        flightPlan.setFromAirportIcao("KMDW");
        flightPlan.setToAirportIcao("KMIA");
        flightPlan.setDistance("1035");
        flightPlan.setFiledAltitude("35000");
        flightPlan.setFiledAirspeed("210");
        flightPlan.setFleetId("d66ba95c-d55a-46f9-9068-2a333b69aa74");
        flightPlan.setEstimatedMinutesEnroute(296);
        flightPlan.setFlightStatus("Ready");
        cache.put("D46581A2-1705-44A0-BEE4-4480F1FC3106", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA208");
        flightPlan.setFromAirportIcao("KMCO");
        flightPlan.setToAirportIcao("KDFW");
        flightPlan.setDistance("877");
        flightPlan.setFiledAltitude("25000");
        flightPlan.setFiledAirspeed("250");
        flightPlan.setFleetId("58e7e179-edf0-4407-b9e1-efffe9d202d3");
        flightPlan.setEstimatedMinutesEnroute(210);
        flightPlan.setFlightStatus("Ready");
        cache.put("FC875737-3144-475D-9F50-71C6B091B2A9", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA265");
        flightPlan.setFromAirportIcao("KMCO");
        flightPlan.setToAirportIcao("KMIA");
        flightPlan.setDistance("167");
        flightPlan.setFiledAltitude("35000");
        flightPlan.setFiledAirspeed("280");
        flightPlan.setFleetId("ba96b780-4f5e-403e-99f9-e9b547272b21");
        flightPlan.setEstimatedMinutesEnroute(36);
        flightPlan.setFlightStatus("Ready");
        cache.put("FC018509-B4B6-49A0-9639-652BA80DDD45", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA908");
        flightPlan.setFromAirportIcao("KORD");
        flightPlan.setToAirportIcao("KLGA");
        flightPlan.setDistance("637");
        flightPlan.setFiledAltitude("20000");
        flightPlan.setFiledAirspeed("210");
        flightPlan.setFleetId("9e78a04e-9830-4450-8941-941f12553f88");
        flightPlan.setEstimatedMinutesEnroute(182);
        flightPlan.setFlightStatus("Ready");
        cache.put("08AD1277-DB78-43F1-81A8-189682F110A1", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA693");
        flightPlan.setFromAirportIcao("KTPA");
        flightPlan.setToAirportIcao("KFLL");
        flightPlan.setDistance("171");
        flightPlan.setFiledAltitude("30000");
        flightPlan.setFiledAirspeed("250");
        flightPlan.setFleetId("4a3531b8-af11-4fc4-8300-023317013a07");
        flightPlan.setEstimatedMinutesEnroute(41);
        flightPlan.setFlightStatus("Ready");
        cache.put("9D073E90-41DB-425A-8EDB-7D0B3CE7A30A", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA475");
        flightPlan.setFromAirportIcao("KCLT");
        flightPlan.setToAirportIcao("KMIA");
        flightPlan.setDistance("572");
        flightPlan.setFiledAltitude("25000");
        flightPlan.setFiledAirspeed("210");
        flightPlan.setFleetId("4863d02a-b8f2-4f0d-9b7a-dbd32ba2dfb3");
        flightPlan.setEstimatedMinutesEnroute(163);
        flightPlan.setFlightStatus("Ready");
        cache.put("10691B2B-A4FA-424A-9D1D-7FA627FAC34C", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA012");
        flightPlan.setFromAirportIcao("KDEN");
        flightPlan.setToAirportIcao("KJFK");
        flightPlan.setDistance("1411");
        flightPlan.setFiledAltitude("25000");
        flightPlan.setFiledAirspeed("280");
        flightPlan.setFleetId("ba96b780-4f5e-403e-99f9-e9b547272b21");
        flightPlan.setEstimatedMinutesEnroute(302);
        flightPlan.setFlightStatus("Ready");
        cache.put("E1ED315D-DBCC-4141-B2C8-6589D6973D37", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA229");
        flightPlan.setFromAirportIcao("KLAS");
        flightPlan.setToAirportIcao("KPDX");
        flightPlan.setDistance("687");
        flightPlan.setFiledAltitude("35000");
        flightPlan.setFiledAirspeed("250");
        flightPlan.setFleetId("d66ba95c-d55a-46f9-9068-2a333b69aa74");
        flightPlan.setEstimatedMinutesEnroute(165);
        flightPlan.setFlightStatus("Ready");
        cache.put("7F9B3873-981A-4BCE-9135-6D165310C4DC", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA888");
        flightPlan.setFromAirportIcao("KDEN");
        flightPlan.setToAirportIcao("KIAD");
        flightPlan.setDistance("1270");
        flightPlan.setFiledAltitude("30000");
        flightPlan.setFiledAirspeed("210");
        flightPlan.setFleetId("2871374c-1e32-495f-a5eb-86bdae04476d");
        flightPlan.setEstimatedMinutesEnroute(363);
        flightPlan.setFlightStatus("Ready");
        cache.put("4ABB5021-2986-40AA-B817-26393ABE1295", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA922");
        flightPlan.setFromAirportIcao("KPHL");
        flightPlan.setToAirportIcao("KDEN");
        flightPlan.setDistance("1360");
        flightPlan.setFiledAltitude("20000");
        flightPlan.setFiledAirspeed("210");
        flightPlan.setFleetId("e8e9816d-57ee-43a8-8d0c-45c07fd5713e");
        flightPlan.setEstimatedMinutesEnroute(389);
        flightPlan.setFlightStatus("Ready");
        cache.put("D669D74F-76F7-40A3-85F8-BC25C6453235", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA787");
        flightPlan.setFromAirportIcao("KJFK");
        flightPlan.setToAirportIcao("KDTW");
        flightPlan.setDistance("442");
        flightPlan.setFiledAltitude("35000");
        flightPlan.setFiledAirspeed("250");
        flightPlan.setFleetId("6f9a00e8-2b9f-4b60-8e10-6501fcb39062");
        flightPlan.setEstimatedMinutesEnroute(106);
        flightPlan.setFlightStatus("Ready");
        cache.put("85CA002F-0949-4E9A-BD6D-37B1E7A3197B", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA435");
        flightPlan.setFromAirportIcao("KLAS");
        flightPlan.setToAirportIcao("KLGA");
        flightPlan.setDistance("1951");
        flightPlan.setFiledAltitude("25000");
        flightPlan.setFiledAirspeed("210");
        flightPlan.setFleetId("d2a153a9-0054-4690-8c72-dcb709ce85f0");
        flightPlan.setEstimatedMinutesEnroute(557);
        flightPlan.setFlightStatus("Ready");
        cache.put("F36654D8-AD85-43BA-92A8-714017C2F796", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA435");
        flightPlan.setFromAirportIcao("KCLT");
        flightPlan.setToAirportIcao("KIAD");
        flightPlan.setDistance("281");
        flightPlan.setFiledAltitude("30000");
        flightPlan.setFiledAirspeed("210");
        flightPlan.setFleetId("0c757e27-5c70-4dca-8298-b17393f07bb3");
        flightPlan.setEstimatedMinutesEnroute(80);
        flightPlan.setFlightStatus("Ready");
        cache.put("7A694914-766C-4043-8FEF-68C985C62801", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA833");
        flightPlan.setFromAirportIcao("KDTW");
        flightPlan.setToAirportIcao("KCLT");
        flightPlan.setDistance("438");
        flightPlan.setFiledAltitude("20000");
        flightPlan.setFiledAirspeed("250");
        flightPlan.setFleetId("e5454e3b-a172-4b0e-b611-88ad4e45d190");
        flightPlan.setEstimatedMinutesEnroute(105);
        flightPlan.setFlightStatus("Ready");
        cache.put("88683EEF-F467-4056-B995-AC1E6BBA3593", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA621");
        flightPlan.setFromAirportIcao("KTPA");
        flightPlan.setToAirportIcao("KCLT");
        flightPlan.setDistance("449");
        flightPlan.setFiledAltitude("35000");
        flightPlan.setFiledAirspeed("250");
        flightPlan.setFleetId("4a3531b8-af11-4fc4-8300-023317013a07");
        flightPlan.setEstimatedMinutesEnroute(108);
        flightPlan.setFlightStatus("Ready");
        cache.put("5CEC6CD9-31CF-40C1-A955-5A53BB1EE988", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA428");
        flightPlan.setFromAirportIcao("KTPA");
        flightPlan.setToAirportIcao("KSFO");
        flightPlan.setDistance("2141");
        flightPlan.setFiledAltitude("30000");
        flightPlan.setFiledAirspeed("210");
        flightPlan.setFleetId("e5454e3b-a172-4b0e-b611-88ad4e45d190");
        flightPlan.setEstimatedMinutesEnroute(612);
        flightPlan.setFlightStatus("Ready");
        cache.put("977999EA-6C35-4A7F-B4C5-6CB6831C52C0", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA026");
        flightPlan.setFromAirportIcao("KMCO");
        flightPlan.setToAirportIcao("KLAS");
        flightPlan.setDistance("1802");
        flightPlan.setFiledAltitude("25000");
        flightPlan.setFiledAirspeed("210");
        flightPlan.setFleetId("9304e3ff-9694-43c8-bdb6-acdd565ec46b");
        flightPlan.setEstimatedMinutesEnroute(515);
        flightPlan.setFlightStatus("Ready");
        cache.put("04F93F26-E61B-4775-A301-6F9DA6BB0348", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA866");
        flightPlan.setFromAirportIcao("KFLL");
        flightPlan.setToAirportIcao("KBOS");
        flightPlan.setDistance("1102");
        flightPlan.setFiledAltitude("20000");
        flightPlan.setFiledAirspeed("210");
        flightPlan.setFleetId("9304e3ff-9694-43c8-bdb6-acdd565ec46b");
        flightPlan.setEstimatedMinutesEnroute(315);
        flightPlan.setFlightStatus("Ready");
        cache.put("080CB6D8-255C-43BC-BE0F-D85C1C0FC66E", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA166");
        flightPlan.setFromAirportIcao("KMDW");
        flightPlan.setToAirportIcao("KLAX");
        flightPlan.setDistance("1526");
        flightPlan.setFiledAltitude("35000");
        flightPlan.setFiledAirspeed("280");
        flightPlan.setFleetId("9e78a04e-9830-4450-8941-941f12553f88");
        flightPlan.setEstimatedMinutesEnroute(327);
        flightPlan.setFlightStatus("Ready");
        cache.put("18B0FCF6-F340-4EA1-900C-B4F9E2FD3777", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA105");
        flightPlan.setFromAirportIcao("KBOS");
        flightPlan.setToAirportIcao("KATL");
        flightPlan.setDistance("825");
        flightPlan.setFiledAltitude("30000");
        flightPlan.setFiledAirspeed("210");
        flightPlan.setFleetId("c90ed53e-425b-412f-8510-1ab95951c400");
        flightPlan.setEstimatedMinutesEnroute(236);
        flightPlan.setFlightStatus("Ready");
        cache.put("FC15FF8C-7701-4C18-BB9A-16CE709C5A89", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA366");
        flightPlan.setFromAirportIcao("KORD");
        flightPlan.setToAirportIcao("KFLL");
        flightPlan.setDistance("1035");
        flightPlan.setFiledAltitude("20000");
        flightPlan.setFiledAirspeed("210");
        flightPlan.setFleetId("9f4d6400-2e2a-47bc-b6f4-76dae10c9ecc");
        flightPlan.setEstimatedMinutesEnroute(296);
        flightPlan.setFlightStatus("Ready");
        cache.put("812005D4-CEBC-4CA0-8F5D-3153600BD465", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA710");
        flightPlan.setFromAirportIcao("KCLT");
        flightPlan.setToAirportIcao("KPDX");
        flightPlan.setDistance("2018");
        flightPlan.setFiledAltitude("30000");
        flightPlan.setFiledAirspeed("280");
        flightPlan.setFleetId("7e33ced7-a897-4a8b-afdd-c7d609636246");
        flightPlan.setEstimatedMinutesEnroute(432);
        flightPlan.setFlightStatus("Ready");
        cache.put("B5270A64-1EA4-4D9D-83DD-55061DA077FD", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA101");
        flightPlan.setFromAirportIcao("KPHX");
        flightPlan.setToAirportIcao("KCLT");
        flightPlan.setDistance("1550");
        flightPlan.setFiledAltitude("35000");
        flightPlan.setFiledAirspeed("210");
        flightPlan.setFleetId("4a3531b8-af11-4fc4-8300-023317013a07");
        flightPlan.setEstimatedMinutesEnroute(443);
        flightPlan.setFlightStatus("Ready");
        cache.put("6BF6C703-92C7-49DC-B017-486F03CD855F", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA524");
        flightPlan.setFromAirportIcao("KSFO");
        flightPlan.setToAirportIcao("KLAX");
        flightPlan.setDistance("294");
        flightPlan.setFiledAltitude("25000");
        flightPlan.setFiledAirspeed("280");
        flightPlan.setFleetId("ba96b780-4f5e-403e-99f9-e9b547272b21");
        flightPlan.setEstimatedMinutesEnroute(63);
        flightPlan.setFlightStatus("Ready");
        cache.put("5715DE0E-1E59-421D-97CA-D1E159ABB6AA", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA586");
        flightPlan.setFromAirportIcao("KLGA");
        flightPlan.setToAirportIcao("KTPA");
        flightPlan.setDistance("886");
        flightPlan.setFiledAltitude("25000");
        flightPlan.setFiledAirspeed("280");
        flightPlan.setFleetId("2871374c-1e32-495f-a5eb-86bdae04476d");
        flightPlan.setEstimatedMinutesEnroute(190);
        flightPlan.setFlightStatus("Ready");
        cache.put("8B353B63-AFA8-4D07-B164-26DA1509F3E2", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA699");
        flightPlan.setFromAirportIcao("KDCA");
        flightPlan.setToAirportIcao("KBOS");
        flightPlan.setDistance("348");
        flightPlan.setFiledAltitude("25000");
        flightPlan.setFiledAirspeed("280");
        flightPlan.setFleetId("c90ed53e-425b-412f-8510-1ab95951c400");
        flightPlan.setEstimatedMinutesEnroute(75);
        flightPlan.setFlightStatus("Ready");
        cache.put("7E935B96-7973-411B-A70E-F47AA437A268", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA146");
        flightPlan.setFromAirportIcao("KLAS");
        flightPlan.setToAirportIcao("KPHL");
        flightPlan.setDistance("1893");
        flightPlan.setFiledAltitude("25000");
        flightPlan.setFiledAirspeed("210");
        flightPlan.setFleetId("e5454e3b-a172-4b0e-b611-88ad4e45d190");
        flightPlan.setEstimatedMinutesEnroute(541);
        flightPlan.setFlightStatus("Ready");
        cache.put("3ABF5632-5416-4611-8151-F750D045FE07", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA571");
        flightPlan.setFromAirportIcao("KATL");
        flightPlan.setToAirportIcao("KEWR");
        flightPlan.setDistance("651");
        flightPlan.setFiledAltitude("30000");
        flightPlan.setFiledAirspeed("280");
        flightPlan.setFleetId("d2a153a9-0054-4690-8c72-dcb709ce85f0");
        flightPlan.setEstimatedMinutesEnroute(140);
        flightPlan.setFlightStatus("Ready");
        cache.put("C7544756-F8AA-4958-8598-8285945E9273", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA185");
        flightPlan.setFromAirportIcao("KIAD");
        flightPlan.setToAirportIcao("KBOS");
        flightPlan.setDistance("360");
        flightPlan.setFiledAltitude("30000");
        flightPlan.setFiledAirspeed("280");
        flightPlan.setFleetId("0393c1e9-58a4-47f7-a15f-e1f1aa73b732");
        flightPlan.setEstimatedMinutesEnroute(77);
        flightPlan.setFlightStatus("Ready");
        cache.put("EA20DB3E-E0E3-4F6C-BDF2-A246A2B2EC77", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA786");
        flightPlan.setFromAirportIcao("KBOS");
        flightPlan.setToAirportIcao("KBWI");
        flightPlan.setDistance("322");
        flightPlan.setFiledAltitude("20000");
        flightPlan.setFiledAirspeed("280");
        flightPlan.setFleetId("9f4d6400-2e2a-47bc-b6f4-76dae10c9ecc");
        flightPlan.setEstimatedMinutesEnroute(69);
        flightPlan.setFlightStatus("Ready");
        cache.put("5D09ECE2-406A-4941-A0DB-B1DA9D0556CD", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA762");
        flightPlan.setFromAirportIcao("KDEN");
        flightPlan.setToAirportIcao("KDTW");
        flightPlan.setDistance("977");
        flightPlan.setFiledAltitude("30000");
        flightPlan.setFiledAirspeed("250");
        flightPlan.setFleetId("0393c1e9-58a4-47f7-a15f-e1f1aa73b732");
        flightPlan.setEstimatedMinutesEnroute(234);
        flightPlan.setFlightStatus("Ready");
        cache.put("B5B6B341-B109-4BC3-B1C0-FCA02B955731", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA071");
        flightPlan.setFromAirportIcao("KATL");
        flightPlan.setToAirportIcao("KEWR");
        flightPlan.setDistance("651");
        flightPlan.setFiledAltitude("35000");
        flightPlan.setFiledAirspeed("210");
        flightPlan.setFleetId("9e78a04e-9830-4450-8941-941f12553f88");
        flightPlan.setEstimatedMinutesEnroute(186);
        flightPlan.setFlightStatus("Ready");
        cache.put("3ADD735E-1269-4A2C-9710-1F3A5EBDB5AE", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA296");
        flightPlan.setFromAirportIcao("KIAD");
        flightPlan.setToAirportIcao("KMDW");
        flightPlan.setDistance("504");
        flightPlan.setFiledAltitude("35000");
        flightPlan.setFiledAirspeed("280");
        flightPlan.setFleetId("b1ebe9ca-5d82-41c3-81e6-7fb9c97cbba2");
        flightPlan.setEstimatedMinutesEnroute(108);
        flightPlan.setFlightStatus("Ready");
        cache.put("CF528C5F-CEF3-407C-AA07-961D35BC43FF", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA889");
        flightPlan.setFromAirportIcao("KDFW");
        flightPlan.setToAirportIcao("KDCA");
        flightPlan.setDistance("1038");
        flightPlan.setFiledAltitude("30000");
        flightPlan.setFiledAirspeed("250");
        flightPlan.setFleetId("7556e4a3-381c-4df8-8dce-99f8b8725418");
        flightPlan.setEstimatedMinutesEnroute(249);
        flightPlan.setFlightStatus("Ready");
        cache.put("EEC383B2-B792-4B2A-8351-56D52565D883", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA517");
        flightPlan.setFromAirportIcao("KDTW");
        flightPlan.setToAirportIcao("KPHL");
        flightPlan.setDistance("397");
        flightPlan.setFiledAltitude("25000");
        flightPlan.setFiledAirspeed("250");
        flightPlan.setFleetId("6f9a00e8-2b9f-4b60-8e10-6501fcb39062");
        flightPlan.setEstimatedMinutesEnroute(95);
        flightPlan.setFlightStatus("Ready");
        cache.put("88DB2E11-6879-47A5-ABF1-42FBF6B0F52B", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA951");
        flightPlan.setFromAirportIcao("KEWR");
        flightPlan.setToAirportIcao("KSFO");
        flightPlan.setDistance("2229");
        flightPlan.setFiledAltitude("30000");
        flightPlan.setFiledAirspeed("210");
        flightPlan.setFleetId("b63b4c89-6966-43f1-9ea6-7dc8b4d2ed6b");
        flightPlan.setEstimatedMinutesEnroute(637);
        flightPlan.setFlightStatus("Ready");
        cache.put("4652B1F0-E8E0-4C54-BA45-A19BF8A29489", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA443");
        flightPlan.setFromAirportIcao("KBOS");
        flightPlan.setToAirportIcao("KSFO");
        flightPlan.setDistance("2355");
        flightPlan.setFiledAltitude("25000");
        flightPlan.setFiledAirspeed("250");
        flightPlan.setFleetId("ad21d75c-0ad1-4fdb-b7e2-b1e0460eb775");
        flightPlan.setEstimatedMinutesEnroute(565);
        flightPlan.setFlightStatus("Ready");
        cache.put("2B900B28-5C5A-4D1D-A3FA-6F6B07BC8ED8", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA042");
        flightPlan.setFromAirportIcao("KORD");
        flightPlan.setToAirportIcao("KIAD");
        flightPlan.setDistance("516");
        flightPlan.setFiledAltitude("35000");
        flightPlan.setFiledAirspeed("210");
        flightPlan.setFleetId("0c757e27-5c70-4dca-8298-b17393f07bb3");
        flightPlan.setEstimatedMinutesEnroute(147);
        flightPlan.setFlightStatus("Ready");
        cache.put("3069D00C-F1F4-456F-94E1-40FFB16E0775", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA701");
        flightPlan.setFromAirportIcao("KSFO");
        flightPlan.setToAirportIcao("KDTW");
        flightPlan.setDistance("1811");
        flightPlan.setFiledAltitude("35000");
        flightPlan.setFiledAirspeed("250");
        flightPlan.setFleetId("b63b4c89-6966-43f1-9ea6-7dc8b4d2ed6b");
        flightPlan.setEstimatedMinutesEnroute(435);
        flightPlan.setFlightStatus("Ready");
        cache.put("B524314F-0888-48CA-A8C4-6CF8C2813A3D", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA688");
        flightPlan.setFromAirportIcao("KLAS");
        flightPlan.setToAirportIcao("KTPA");
        flightPlan.setDistance("1773");
        flightPlan.setFiledAltitude("25000");
        flightPlan.setFiledAirspeed("280");
        flightPlan.setFleetId("0c757e27-5c70-4dca-8298-b17393f07bb3");
        flightPlan.setEstimatedMinutesEnroute(380);
        flightPlan.setFlightStatus("Ready");
        cache.put("A13831F8-B0D0-4290-9FFB-DA71E4EB767D", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA167");
        flightPlan.setFromAirportIcao("KDCA");
        flightPlan.setToAirportIcao("KEWR");
        flightPlan.setDistance("173");
        flightPlan.setFiledAltitude("20000");
        flightPlan.setFiledAirspeed("210");
        flightPlan.setFleetId("9f4d6400-2e2a-47bc-b6f4-76dae10c9ecc");
        flightPlan.setEstimatedMinutesEnroute(49);
        flightPlan.setFlightStatus("Ready");
        cache.put("942E6764-E22D-4F9B-A291-55455D09653D", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA987");
        flightPlan.setFromAirportIcao("KPHL");
        flightPlan.setToAirportIcao("KFLL");
        flightPlan.setDistance("879");
        flightPlan.setFiledAltitude("35000");
        flightPlan.setFiledAirspeed("280");
        flightPlan.setFleetId("d2a153a9-0054-4690-8c72-dcb709ce85f0");
        flightPlan.setEstimatedMinutesEnroute(188);
        flightPlan.setFlightStatus("Ready");
        cache.put("D5562907-46CF-49BF-B796-13C3E6DD90E6", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA603");
        flightPlan.setFromAirportIcao("KMDW");
        flightPlan.setToAirportIcao("KSFO");
        flightPlan.setDistance("1613");
        flightPlan.setFiledAltitude("30000");
        flightPlan.setFiledAirspeed("280");
        flightPlan.setFleetId("9304e3ff-9694-43c8-bdb6-acdd565ec46b");
        flightPlan.setEstimatedMinutesEnroute(346);
        flightPlan.setFlightStatus("Ready");
        cache.put("363884BF-46C2-48A8-9AFB-EC9587FE2290", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA025");
        flightPlan.setFromAirportIcao("KTPA");
        flightPlan.setToAirportIcao("KORD");
        flightPlan.setDistance("885");
        flightPlan.setFiledAltitude("25000");
        flightPlan.setFiledAirspeed("280");
        flightPlan.setFleetId("806dbd82-f4c7-464c-8499-4e5363c71b15");
        flightPlan.setEstimatedMinutesEnroute(190);
        flightPlan.setFlightStatus("Ready");
        cache.put("6BE9EDCC-97FC-4C07-B07E-7D0FE2F4E820", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA499");
        flightPlan.setFromAirportIcao("KTPA");
        flightPlan.setToAirportIcao("KMCO");
        flightPlan.setDistance("70");
        flightPlan.setFiledAltitude("25000");
        flightPlan.setFiledAirspeed("210");
        flightPlan.setFleetId("806dbd82-f4c7-464c-8499-4e5363c71b15");
        flightPlan.setEstimatedMinutesEnroute(20);
        flightPlan.setFlightStatus("Ready");
        cache.put("E0EFCA84-EA7D-483B-85E5-6A11DACD5433", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA938");
        flightPlan.setFromAirportIcao("KDCA");
        flightPlan.setToAirportIcao("KLAX");
        flightPlan.setDistance("2010");
        flightPlan.setFiledAltitude("20000");
        flightPlan.setFiledAirspeed("250");
        flightPlan.setFleetId("c90ed53e-425b-412f-8510-1ab95951c400");
        flightPlan.setEstimatedMinutesEnroute(482);
        flightPlan.setFlightStatus("Ready");
        cache.put("8CFA5882-A88D-45A6-BFAF-4E4FBD292FEC", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA469");
        flightPlan.setFromAirportIcao("KEWR");
        flightPlan.setToAirportIcao("KDTW");
        flightPlan.setDistance("424");
        flightPlan.setFiledAltitude("35000");
        flightPlan.setFiledAirspeed("210");
        flightPlan.setFleetId("c90ed53e-425b-412f-8510-1ab95951c400");
        flightPlan.setEstimatedMinutesEnroute(121);
        flightPlan.setFlightStatus("Ready");
        cache.put("07D97370-A1EE-4B30-B1DA-A33FD62FD826", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA752");
        flightPlan.setFromAirportIcao("KDCA");
        flightPlan.setToAirportIcao("KBOS");
        flightPlan.setDistance("348");
        flightPlan.setFiledAltitude("20000");
        flightPlan.setFiledAirspeed("250");
        flightPlan.setFleetId("0875ba1c-baa4-4edd-abe8-744ed473826b");
        flightPlan.setEstimatedMinutesEnroute(84);
        flightPlan.setFlightStatus("Ready");
        cache.put("4B90B82B-8265-475B-8FA6-B28CDC068657", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA987");
        flightPlan.setFromAirportIcao("KMSP");
        flightPlan.setToAirportIcao("KDEN");
        flightPlan.setDistance("601");
        flightPlan.setFiledAltitude("25000");
        flightPlan.setFiledAirspeed("250");
        flightPlan.setFleetId("2871374c-1e32-495f-a5eb-86bdae04476d");
        flightPlan.setEstimatedMinutesEnroute(144);
        flightPlan.setFlightStatus("Ready");
        cache.put("BC19C9B5-3874-402C-91A0-2D80DDF98AF1", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA505");
        flightPlan.setFromAirportIcao("KMSP");
        flightPlan.setToAirportIcao("KDTW");
        flightPlan.setDistance("465");
        flightPlan.setFiledAltitude("35000");
        flightPlan.setFiledAirspeed("250");
        flightPlan.setFleetId("4a3531b8-af11-4fc4-8300-023317013a07");
        flightPlan.setEstimatedMinutesEnroute(112);
        flightPlan.setFlightStatus("Ready");
        cache.put("1F94E112-EC01-4B19-BD57-11591EEB0134", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA399");
        flightPlan.setFromAirportIcao("KJFK");
        flightPlan.setToAirportIcao("KDCA");
        flightPlan.setDistance("185");
        flightPlan.setFiledAltitude("25000");
        flightPlan.setFiledAirspeed("210");
        flightPlan.setFleetId("7e33ced7-a897-4a8b-afdd-c7d609636246");
        flightPlan.setEstimatedMinutesEnroute(53);
        flightPlan.setFlightStatus("Ready");
        cache.put("54D7D9E3-72CF-4FB8-9725-9EF0B0722B1C", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA348");
        flightPlan.setFromAirportIcao("KLAS");
        flightPlan.setToAirportIcao("KPHL");
        flightPlan.setDistance("1893");
        flightPlan.setFiledAltitude("30000");
        flightPlan.setFiledAirspeed("250");
        flightPlan.setFleetId("2871374c-1e32-495f-a5eb-86bdae04476d");
        flightPlan.setEstimatedMinutesEnroute(454);
        flightPlan.setFlightStatus("Ready");
        cache.put("0F61B211-7126-4B3C-B6A5-00AD1A499A78", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA250");
        flightPlan.setFromAirportIcao("KDTW");
        flightPlan.setToAirportIcao("KCLT");
        flightPlan.setDistance("438");
        flightPlan.setFiledAltitude("30000");
        flightPlan.setFiledAirspeed("280");
        flightPlan.setFleetId("d2a153a9-0054-4690-8c72-dcb709ce85f0");
        flightPlan.setEstimatedMinutesEnroute(94);
        flightPlan.setFlightStatus("Ready");
        cache.put("2410222A-FDE3-4846-ABEB-23E5A58C250C", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA889");
        flightPlan.setFromAirportIcao("KSFO");
        flightPlan.setToAirportIcao("KORD");
        flightPlan.setDistance("1608");
        flightPlan.setFiledAltitude("20000");
        flightPlan.setFiledAirspeed("280");
        flightPlan.setFleetId("95794afd-d1ff-4b08-b287-3c59a4799a44");
        flightPlan.setEstimatedMinutesEnroute(345);
        flightPlan.setFlightStatus("Ready");
        cache.put("5333EC9E-DD21-49C0-8D79-90D30650A288", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA600");
        flightPlan.setFromAirportIcao("KSAN");
        flightPlan.setToAirportIcao("KLAS");
        flightPlan.setDistance("231");
        flightPlan.setFiledAltitude("30000");
        flightPlan.setFiledAirspeed("210");
        flightPlan.setFleetId("c90ed53e-425b-412f-8510-1ab95951c400");
        flightPlan.setEstimatedMinutesEnroute(66);
        flightPlan.setFlightStatus("Ready");
        cache.put("DB8F3A98-4E21-40C5-87BA-24ACB72F0CF6", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA321");
        flightPlan.setFromAirportIcao("KBWI");
        flightPlan.setToAirportIcao("KPDX");
        flightPlan.setDistance("2056");
        flightPlan.setFiledAltitude("25000");
        flightPlan.setFiledAirspeed("250");
        flightPlan.setFleetId("58e7e179-edf0-4407-b9e1-efffe9d202d3");
        flightPlan.setEstimatedMinutesEnroute(493);
        flightPlan.setFlightStatus("Ready");
        cache.put("DED3C74F-0780-4AD1-95A7-09D6219C28DE", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA095");
        flightPlan.setFromAirportIcao("KPHX");
        flightPlan.setToAirportIcao("KMSP");
        flightPlan.setDistance("1133");
        flightPlan.setFiledAltitude("30000");
        flightPlan.setFiledAirspeed("210");
        flightPlan.setFleetId("b63b4c89-6966-43f1-9ea6-7dc8b4d2ed6b");
        flightPlan.setEstimatedMinutesEnroute(324);
        flightPlan.setFlightStatus("Ready");
        cache.put("A2039D19-4800-46A2-92D6-7B491D433656", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA631");
        flightPlan.setFromAirportIcao("KATL");
        flightPlan.setToAirportIcao("KFLL");
        flightPlan.setDistance("510");
        flightPlan.setFiledAltitude("30000");
        flightPlan.setFiledAirspeed("250");
        flightPlan.setFleetId("58e7e179-edf0-4407-b9e1-efffe9d202d3");
        flightPlan.setEstimatedMinutesEnroute(122);
        flightPlan.setFlightStatus("Ready");
        cache.put("ABBFEE7B-25E5-4A57-87A6-5E9FB1F07A50", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA816");
        flightPlan.setFromAirportIcao("KPDX");
        flightPlan.setToAirportIcao("KMDW");
        flightPlan.setDistance("1526");
        flightPlan.setFiledAltitude("30000");
        flightPlan.setFiledAirspeed("280");
        flightPlan.setFleetId("806dbd82-f4c7-464c-8499-4e5363c71b15");
        flightPlan.setEstimatedMinutesEnroute(327);
        flightPlan.setFlightStatus("Ready");
        cache.put("43EB9785-E9D1-437D-8A4B-75F096D50C71", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA296");
        flightPlan.setFromAirportIcao("KJFK");
        flightPlan.setToAirportIcao("KDFW");
        flightPlan.setDistance("1212");
        flightPlan.setFiledAltitude("30000");
        flightPlan.setFiledAirspeed("210");
        flightPlan.setFleetId("d2a153a9-0054-4690-8c72-dcb709ce85f0");
        flightPlan.setEstimatedMinutesEnroute(346);
        flightPlan.setFlightStatus("Ready");
        cache.put("80665B12-62A6-4FE2-A7FA-2DE1B7E86189", flightPlan);


        flightPlan = new FlightPlans();
        flightPlan.setFlightNumber("DA878");
        flightPlan.setFromAirportIcao("KDEN");
        flightPlan.setToAirportIcao("KMCO");
        flightPlan.setDistance("1378");
        flightPlan.setFiledAltitude("25000");
        flightPlan.setFiledAirspeed("280");
        flightPlan.setFleetId("0c757e27-5c70-4dca-8298-b17393f07bb3");
        flightPlan.setEstimatedMinutesEnroute(295);
        flightPlan.setFlightStatus("Ready");
        cache.put("4F403DCF-9DC2-4C4E-92F3-08C8CCF2DDC3", flightPlan);

    }
}
