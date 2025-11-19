#README for some fo the docker steps

**Install Docker**  <br />
sudo yum update -y    <br />
sudo yum -y install docker   <br />
**Start Docker**     <br />
sudo service docker start   <br />
**Access Docker commands in ec2-user user**   <br />
sudo usermod -a -G docker ec2-user   <br />
sudo chmod 666 /var/run/docker.sock   <br />
docker version   <br />
**in cloudshell**   <br />
docker login         <br />
docker image pull shengchunjob/sideprojects_job:V1.0   <br />
docker run -p 80:8080 shengchunjob/sideprojects_job:V1.0  <br />
make sure run it at port 80 which is the default HTTP port  <br />

http://3.19.234.244/countryNamePath?id=1 (exampl access url, make sure it is http NOT https)
