# rabobank_cts_task
Assignment from cts for backend and frontend code development.

I have developed application to upload csv/xml file and populate in UI:

Techonlogy used:
Angular -6
angular datatable
Node -v8.11.1
SpringBoot -2.1.4
Spring core and web - 5.1.6
apache commons-csv -1.5


Below command need to execute /dist folder for production code deployment,
folderpath/rabobank-inventory>npm install express --save
folderpath/rabobank-inventory>npm i http-proxy-middleware
folderpath/rabobank-inventory>npm i path
Execute Node server:
folderpath/rabobank-inventory>node proxyserver.js


Below command need to execute for developing code deployment,
folderpath/rabobank-inventory>npm install
Execute Node server:
folderpath/rabobank-inventory>ng serve


Backend:
folderpath/rabo_bank_inventory/>mvn clean spring-boot:run


Frontend application access URL:contacts
http://localhost:4200/frontendtask

Backend application access URL:Bankdetails
http://localhost:4200/backendtask

Backend Rest api call:
http://localhost:8082/api/fileupload
Post param:
file:(binary data)

