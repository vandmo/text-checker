set -euxo pipefail

mvn versions:set \
  --file maven-plugin/pom.xml \
  --batch-mode \
  --define newVersion=${TRAVIS_TAG}

ls -larth deployment
gpg2 --keyring=${TRAVIS_BUILD_DIR}/pubring.gpg --no-default-keyring --import deployment/signingkey.asc
gpg2 --allow-secret-key-import --keyring=${TRAVIS_BUILD_DIR}/secring.gpg --no-default-keyring --import deployment/signingkey.asc

mvn clean deploy \
  --file maven-plugin/pom.xml \
  --batch-mode \
  --settings deployment/settings.xml \
  --define gpg.executable=gpg2 \
  --define gpg.keyname=B07746099C297F239C1C4343E16B0657F1774582 \
  --define gpg.passphrase=${PASSPHRASE} \
  --define gpg.publicKeyring=${TRAVIS_BUILD_DIR}/pubring.gpg \
  --define gpg.secretKeyring=${TRAVIS_BUILD_DIR}/secring.gpg \
  --define maven.skip.install=true
