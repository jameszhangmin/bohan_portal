cd ../bohan_dao/bohan/
echo 'cd bohan_dao/bohan/'

mvn install

echo 'maven intall complete'
cp bohan-dao/target/bohan-dao-0.0.1-SNAPSHOT.jar ../../bohan_portal/lib/bohan-dao-0.0.1-SNAPSHOT.jar

echo 'dao copy complete'
cp bohan-srv/target/bohan-srv-0.0.1-SNAPSHOT.jar ../../bohan_portal/lib/bohan-srv-0.0.1-SNAPSHOT.jar

echo 'service copy complete'
