require 'mustache'
require 'json'

contents = []
Dir.glob('examples/*.json') do |file|
  contents.push(
      title: JSON.parse(File.read(file))['title'],
      link: file.sub('examples/', '').sub('.json', '.md')
  )
end

rendered = Mustache.render("{{#.}}\n- [{{title}}]({{link}})\n{{/.}}", contents)
File.write('../documentation/README.md', rendered)
