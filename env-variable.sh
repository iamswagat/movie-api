echo "load database properties -: started" 
export MYSQL_DATABASE=moviedb
export MYSQL_USER=root
export MYSQL_PASSWORD=root
export MYSQL_CI_URL=jdbc:mysql://localhost:3306/moviedb?createDatabaseIfNotExist=true
echo "load database properties -: ended"
