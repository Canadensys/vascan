cd ~/
git clone https://github.com/Canadensys/canadensys-data-access.git
cd canadensys-data-access/
mvn install
cd ~/
git clone https://github.com/Canadensys/canadensys-web-core.git
cd canadensys-web-core/
mvn clean install
cd ~/
git clone https://github.com/Canadensys/canadensys-web-theme.git
cd canadensys-web-theme/
mvn clean install
cd ~/
git clone https://github.com/Canadensys/vascan.git
cd vascan/
git checkout fix/build_war
chmod +x gradlew
./gradlew war
