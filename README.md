#README for some fo the docker steps

**Install Docker**
sudo yum update -y
sudo yum -y install docker
**Start Docker**
sudo service docker start
**Access Docker commands in ec2-user user**
sudo usermod -a -G docker ec2-user
sudo chmod 666 /var/run/docker.sock
docker version
**in cloudshell**
docker login
docker image pull shengchunjob/sideprojects_job:V1.0
docker run -p 80:8080 shengchunjob/sideprojects_job:V1.0
make sure run it at port 80 which is the default HTTP port 
