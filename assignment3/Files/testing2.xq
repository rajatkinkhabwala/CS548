declare namespace pat="http://www.example.org/schemas/clinic/patient";
declare namespace cli="http://www.example.org/schemas/clinic";


import module namespace solve = "http://www.example.org/xquery/clinic/solution" at "solutions.xq";

let $db :="instance1.xml"

return solve:getPatientDrugs('104',$db)