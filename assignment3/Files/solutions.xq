module namespace solve = "http://www.example.org/xquery/clinic/solution";                               (:Main Module:)
declare namespace p = "http://www.example.org/schemas/clinic/patient";                                              
declare namespace c = "http://www.example.org/schemas/clinic";
declare namespace t = "http://www.example.org/schemas/clinic/treatment";
declare namespace pro = "http://www.example.org/schemas/clinic/provider";
declare namespace n = "http://www.example.org/schemas/newDB";
declare namespace db = "http://basex.org/modules/db";
(:import schema namespace c = "http://www.example.org/schemas/clinic" at "Clinic.xsd";:)

declare function solve:getPatientTreatments($pid as xs:string,$db as xs:string)                (:pid as Patient ID and db as database node:)
as element()*
{
   for $search in doc($db)/c:Clinic/p:Patient
   where $search/p:patient-id/text() = $pid
   return 
    for $n in $search//p:treatment/node() return
            typeswitch ($n)
            case $f as element(t:Radiology)                                                                     (:typeswitch for type of treatments:)
                return 
                    for $d in $f
                    return  <PatientTreatmentInfo>
                              <Date>{  for $x in $f/t:date 
                                return <DateofTreatment> { $x/text() }</DateofTreatment>}
                                </Date>
                                <RadiologistName>{ $d/t:radiologist/text()}</RadiologistName>
                                <Diagnosis>{$d/t:diagnosis/text()}</Diagnosis>
                             </PatientTreatmentInfo>
            case $f as element(t:Surgery)
               return 
                    for $d in $f
                    return  <PatientTreatmentInfo>
                               <DateofSurgery> { $d/t:date/text() }</DateofSurgery>
                                <SurgeonName>{ $d/t:surgeon/text()}</SurgeonName>
                                <Diagnosis>{$d/t:diagnosis/text()}</Diagnosis>
                             </PatientTreatmentInfo>
            case $f as element(t:drug-treatment)
               return 
                    for $d in $f
                    return  <PatientTreatmentInfo>
                              <DrugName> { $d/t:name/text() }</DrugName>
                                <PhysicianName>{ $d/t:prescribingPhysician/text()}</PhysicianName>
                                <Diagnosis>{$d/t:diagnosis/text()}</Diagnosis>
                                <Dosage>  {$d/t:dosage/text() }  </Dosage>
                             </PatientTreatmentInfo>
            default return ()
};


declare function solve:getPatientDrugs($pid as xs:string, $db as xs:string)                                        (:pid as Patient ID and db as Database node:)
as element()*
{
   (: for $n in doc("instance1.xml")/$db/p:Patient//p:treatment/node() 
    return :)
               for $search in doc($db)/c:Clinic/p:Patient
                    where $search/p:patient-id/text() = $pid                                                     (:drug if prescribed matches with patient id:)
                  return <DrugInfo>                                                                            
                            <DrugName>{  $search/p:treatments//t:name/text()  }</DrugName>
                            <Diagnosis>{  $search/p:treatments//t:diagnosis/text()  }</Diagnosis>
                            <Dosage>{  $search/p:treatments//t:dosage/text() }</Dosage>
                            </DrugInfo>
};




declare function solve:getTreatmentInfo($db as xs:string)                                              (:treatment records of all patients:)
as element()*
{
    
   for $x in doc($db)/c:Clinic
   return 
     for $d in $x/p:Patient
        return <result>
                 <PatientID>{ $d/p:patient-id/text() }</PatientID>
                 <Treatment>  {  solve:getPatientTreatments($d/p:patient-id/text(),$db)    }   </Treatment>
                 <Provider>   { $d//p:treatment/t:provider-id/text() }   </Provider>
                </result>
   
};                   
 
declare function solve:getProviderInfo($db as xs:string)
as element()*
{                                                                                               (:Provider info and the patients they provide to:)
    
    for $pro in doc($db)/c:Clinic
    return <result>
        {
            for $t in $pro/pro:Provider
                
                     return <Info>
                                <ProviderName>{ $t/pro:name/text() }</ProviderName>
                                <Specialization>{ $t/pro:specialization/text() }</Specialization>
                                 <Patients>
                                   {    for $r in doc($db)/c:Clinic/p:Patient
                                        where $r/p:treatments//p:treatment/t:provider-id/text()=$t/pro:provider-id/text()
                                            return <PatientInfo>
                                                                <PatientID>{$r/p:patient-id/text()}</PatientID>
                                                                <PatientName>{ $r/p:name/text() } </PatientName>
                                                                 <Treatment> {   solve:getPatientTreatments($r/p:patient-id/text(),$db)    }</Treatment>   
                                                    </PatientInfo>  
                                        
                             }</Patients>
                            </Info>
              }       
        </result>
    
};

declare function solve:getDrugInfo($db as xs:string)                                                               (:Drug information and patients who take that drug:)
as element()*
{
     for $drug in doc($db)/c:Clinic
     return <function>
                {
                            for $d in $drug/p:Patient//p:treatment/t:drug-treatment/t:name
                            return <result>
                                    <DrugName>      {      $d/text()      }      </DrugName>
                                     <Patients>{ for $s in doc($db)/c:Clinic/p:Patient
                                                where  $s/p:treatments/p:treatment/t:drug-treatment/t:name = $d/text() 
                                                return <PatientDrugInfo>
                                                                <PatientName>{ $s/p:name/text() }</PatientName>
                                                                <TreatmentInfo>{  solve:getPatientTreatments($s/p:patient-id/text(),$db) }</TreatmentInfo>
                                                                <Provider>{   $s/p:treatments//t:provider-id/text()  }</Provider>
                                                       </PatientDrugInfo>   
                                      } </Patients>
                                      </result>
                                
                            
                }
            </function>
     
};