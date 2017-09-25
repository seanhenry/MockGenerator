APPCODE_BUILD=173.2463
IDEA_PATH=community

.PHONY: bootstrap downloadcommunity cpall cplibs cpmodules cpcompiler xmlstarlet

bootstrap: downloadcommunity cpall

downloadcommunity:
	if [ -d "$(IDEA_PATH)/.git" ]; then \
	  cd $(IDEA_PATH); \
	  git fetch --depth 1 origin $(APPCODE_BUILD); \
	  git checkout -b $(APPCODE_BUILD) FETCH_HEAD; \
	else \
	  git clone --depth 1 https://github.com/JetBrains/intellij-community.git $(IDEA_PATH) -b $(APPCODE_BUILD); \
	fi;

cpall: cpmodules cplibs cpcompiler

cpmodules: xmlstarlet
	cp -f $(IDEA_PATH)/.idea/modules.xml modules.xml
	# making project dir relative to this project
	sed -i '' 's/\$$PROJECT_DIR\$$/\$$PROJECT_DIR\$$\/$(IDEA_PATH)/g' modules.xml
	xml ed \
	 -a '/project/component/modules/module[last()]' -t elem -name 'module' -v '' \
	 -i '/project/component/modules/module[last()]' -t attr -name 'fileurl' -v 'file://$$PROJECT_DIR$$/MockGenerator/MockGenerator.iml' \
	 -i '/project/component/modules/module[last()]' -t attr -name 'filepath' -v '$$PROJECT_DIR$$/MockGenerator/MockGenerator.iml' \
	 -a '/project/component/modules/module[last()]' -t elem -name 'module' -v '' \
	 -i '/project/component/modules/module[last()]' -t attr -name 'fileurl' -v 'file://$$PROJECT_DIR$$/UseCases/MockGeneratorUseCases.iml' \
	 -i '/project/component/modules/module[last()]' -t attr -name 'filepath' -v '$$PROJECT_DIR$$/UseCases/MockGeneratorUseCases.iml' \
	modules.xml > .idea/modules.xml
	rm modules.xml

xmlstarlet:
	if hash xml 2>/dev/null; then \
        brew upgrade xmlstarlet || true; \
    else \
        brew install xmlstarlet; \
    fi

cplibs:
	rm -r .idea/libraries || true
	cp -rf $(IDEA_PATH)/.idea/libraries .idea
	# making project dir relative to this project
	find .idea/libraries -type f | xargs sed -i '' 's/\$$PROJECT_DIR\$$/\$$PROJECT_DIR\$$\/$(IDEA_PATH)/g'

cpcompiler:
	cp -f $(IDEA_PATH)/.idea/compiler.xml .idea/compiler.xml
	# making project dir relative to this project
	sed -i '' 's/\$$PROJECT_DIR\$$/\$$PROJECT_DIR\$$\/$(IDEA_PATH)/g' .idea/compiler.xml
