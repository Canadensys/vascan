INSTANCE="travisci/ci-garnet:packer-1512502276-986baf0"
BUILDID="build-$RANDOM"
docker run --name $BUILDID -v ./results:/results -v ./build_in_container.sh:/home/travis/build_in_container.sh -v./copy_to_results.sh:/root/copy_to_results.sh -dit $INSTANCE /sbin/init
docker exec -t $BUILDID bash -c 'chmod +x /home/travis/build_in_container.sh'
docker exec -t $BUILDID su - travis -c "/bin/bash /home/travis/build_in_container.sh"
docker exec -t $BUILDID su -c 'chmod +x /root/copy_to_results.sh'
docker exec -t $BUILDID /bin/bash -c '/root/copy_to_results.sh'
docker stop $BUILDID
docker rm $BUILDID
