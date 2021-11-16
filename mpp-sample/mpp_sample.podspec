Pod::Spec.new do |spec|
    spec.name                     = 'mpp_sample'
    spec.version                  = '1.0.0'
    spec.homepage                 = 'https://github.com/AAkira/Napier'
    spec.source                   = { :git => "Not Published", :tag => "Cocoapods/#{spec.name}/#{spec.version}" }
    spec.authors                  = ''
    spec.license                  = ''
    spec.summary                  = 'CocoaPods library'

    spec.vendored_frameworks      = "build/cocoapods/framework/mpp_sample.framework"
    spec.libraries                = "c++"
    spec.module_name              = "#{spec.name}_umbrella"

                

                

    spec.pod_target_xcconfig = {
        'KOTLIN_PROJECT_PATH' => ':mpp-sample',
        'PRODUCT_MODULE_NAME' => 'mpp_sample',
    }

    spec.script_phases = [
        {
            :name => 'Build mpp_sample',
            :execution_position => :before_compile,
            :shell_path => '/bin/sh',
            :script => <<-SCRIPT
                if [ "YES" = "$COCOAPODS_SKIP_KOTLIN_BUILD" ]; then
                  echo "Skipping Gradle build task invocation due to COCOAPODS_SKIP_KOTLIN_BUILD environment variable set to \"YES\""
                  exit 0
                fi
                set -ev
                REPO_ROOT="$PODS_TARGET_SRCROOT"
                "$REPO_ROOT/../gradlew" -p "$REPO_ROOT" $KOTLIN_PROJECT_PATH:syncFramework \
                    -Pkotlin.native.cocoapods.platform=$PLATFORM_NAME \
                    -Pkotlin.native.cocoapods.archs="$ARCHS" \
                    -Pkotlin.native.cocoapods.configuration=$CONFIGURATION
            SCRIPT
        }
    ]
end