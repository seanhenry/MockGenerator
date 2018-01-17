require 'mustache'
require 'json'

class Markdown < Mustache
end
class UnitTest < Mustache
end

md = Markdown.new
unit = UnitTest.new
Dir.glob('examples/*.json') do |file|
  hash = JSON.parse(File.read(file))
  md[:item] = hash
  unit[:item] = hash
  out = file.sub('examples/', 'output/')
  File.write(out.sub('.json', '.md'), md.render)
  File.write(out.sub('.json', '.swift'), unit.render)
end

system('ruby tableofcontents.rb')
system('ruby mdcopy.rb')
system('ruby swifttest.rb')
