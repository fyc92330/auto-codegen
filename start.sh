MODE=S
PROFILE=default
#PROJECT=codegen-0.0.1-SNAPSHOT.jar
JAR=build/libs/codegen-0.0.1-SNAPSHOT.jar

function builder(){
  MODE=B
  read -p "請輸入專案配置身份: " activeProject
  PROFILE=$activeProject
}


while true; do
read -p "是否啟用佇立者模式建立專案配置? " isStander
  case $isStander in
    [Yy]* )
      break
      ;;
    [Nn]* )
      builder
      break
      ;;
    * )
      echo "請輸入(y/n)"
      ;;
  esac
done


echo ${JAR} + ${MODE} + ${PROFILE}

if [[ "$PROFILE" == "default" ]]; then
  java -jar $JAR --mode=$MODE
else
  java -jar $JAR --spring.profiles.active=$PROFILE --mode=$MODE
fi

