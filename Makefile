APPCODE_BUILD=181.4445

.PHONY: bootstrap downloadcommunity updateandroid updateandroidtools buildcommunity ant

bootstrap: downloadcommunity updateandroid updateandroidtools buildcommunity

# 1 - path to repo
# 2 - remote url
define update_repo
	if [ -d "$(1)/.git" ]; then \
	  cd $(1); \
	  git fetch --depth 1 origin $(APPCODE_BUILD); \
	  git checkout -b $(APPCODE_BUILD) FETCH_HEAD; \
	else \
	  git clone --depth 1 $(2) $(1) -b $(APPCODE_BUILD); \
	fi;
endef

downloadcommunity:
	$(call update_repo,community,https://github.com/JetBrains/intellij-community.git)

updateandroid:
	cd community; \
	$(call update_repo,android,git://git.jetbrains.org/idea/android.git)

updateandroidtools:
	cd community/android; \
	$(call update_repo,tools-base,git://git.jetbrains.org/idea/adt-tools-base.git)

buildcommunity: ant
	cd community; \
	ant

ant:
	if hash ant 2>/dev/null; then \
		brew upgrade ant || true; \
	else \
        brew install ant; \
    fi

