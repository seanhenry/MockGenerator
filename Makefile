APPCODE_BUILD=173.2463

.PHONY: update cplibs cpmodules cpcompiler xmlstarlet

update: cpmodules cplibs cpcompiler
	cd community; \
	git fetch --depth 1 origin $(APPCODE_BUILD); \
	git checkout -b $(APPCODE_BUILD) FETCH_HEAD;

cpcompiler:
	cp -f community/.idea/compiler.xml .idea/compiler.xml
	# making project dir relative to this project
	sed -i '' 's\$PROJECT_DIR\$/\$PROJECT_DIR\$\/community/g' .idea/compiler.xml

cpmodules: xmlstarlet
	cp -f community/.idea/modules.xml modules.xml
	# making project dir relative to this project
	sed -i '' 's/\$$PROJECT_DIR\$$/\$$PROJECT_DIR\$$\/community/g' modules.xml
	xml ed \
	 -a '/project/component/modules/module[last()]' -t elem -name 'module' -v '' \
	 -i '/project/component/modules/module[last()]' -t attr -name 'fileurl' -v 'file://$$PROJECT_DIR$$/MockGenerator/MockGenerator.iml' \
	 -i '/project/component/modules/module[last()]' -t attr -name 'filepath' -v '$$PROJECT_DIR$$/MockGenerator/MockGenerator.iml' \
	 -a '/project/component/modules/module[last()]' -t elem -name 'module' -v '' \
	 -i '/project/component/modules/module[last()]' -t attr -name 'fileurl' -v 'file://$$PROJECT_DIR$$/UseCases/MockGeneratorUseCases.iml' \
	 -i '/project/component/modules/module[last()]' -t attr -name 'filepath' -v '$$PROJECT_DIR$$/UseCases/MockGeneratorUseCases.iml' \
	modules.xml > .idea/modules.xml
	rm modules.xml

cplibs:
	rm -r .idea/libraries || true
	cp -rf community/.idea/libraries .idea
	# making project dir relative to this project
	find .idea/libraries -type f | xargs sed -i '' 's/\$$PROJECT_DIR\$$/\$$PROJECT_DIR\$$\/community/g'

xmlstarlet:
	brew update
	if hash xml 2>/dev/null; then \
        brew upgrade xmlstarlet || true; \
    else \
        brew install xmlstarlet; \
    fi
