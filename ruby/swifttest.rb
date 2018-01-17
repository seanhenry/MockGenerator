system("rm -r swift")
Dir.glob('output/*.swift') do |file|
  name = file.sub('output/', '').sub('.swift', '')
  dir_name = "swift/#{name}"
  system("mkdir -p #{dir_name}")
  Dir.chdir("#{dir_name}") do
    system('swift package init --type library', out: '/dev/null')
    system("cp -f ../../output/#{name}.swift Tests/#{name}Tests/#{name}Tests.swift")
    abort("Test failed for #{name}") unless system("swift test", out: $stdout)
  end
end
