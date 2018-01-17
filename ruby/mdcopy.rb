Dir.glob('output/*.md') do |file|
  system("cp -f #{file} ../documentation")
end
